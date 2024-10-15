package com.acma.properties.outbound;

import com.acma.properties.models.Users;
import com.acma.properties.utility.APIUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Outbound API class for managing user interactions.
 * Handles user-related API calls like fetching users, creating users,
 * and provisioning/de-provisioning users under specific groups.
 *
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-09-18
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AcmaUserOutBoundAPI {

    private final RestTemplate restTemplate;

    @Value("${acma.iam.usersApi}")
    private String usersApiUrl;

    @Value("${acma.iam.groups.property-owners}")
    private String propertyOwnersGroupId;

    @Value("${acma.iam.groupsApi}")
    private String groupsApiUrl;


    /**
     * Fetches all users from the IAM Keyclock service.
     *
     * @param accessToken the access token for authentication
     * @return  list of users
     */
    public List<Users> getAllUsers(String accessToken) {
        log.info("Fetching all users using API: {}", usersApiUrl);
        try {
            HttpEntity<String> entity = new HttpEntity<>(APIUtils.buildHeaders(accessToken));
            ResponseEntity<?> responseEntity = restTemplate.exchange(usersApiUrl, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<Users>>() {});
            return processUsersList(responseEntity);
        } catch (Exception e) {
            log.error("Error occurred while fetching users", e);
            throw new RuntimeException("Error occurred while fetching users", e);
        }
    }

    /**
     * Fetches all users by calling the group API with the given group ID.
     *
     * @param groupId the ID of the group
     * @param accessToken the access token for authentication
     * @return UsersResponse containing a list of users in the group
     */
    public List<Users> getUsersByGroupId(String groupId, String accessToken) {
        String apiEndpoint = groupsApiUrl + groupId + "/members";
        log.info("Fetching users for group ID {} using API: {}", groupId, apiEndpoint);
        try {
            HttpEntity<String> entity = new HttpEntity<>(APIUtils.buildHeaders(accessToken));
            ResponseEntity<?> responseEntity = restTemplate.exchange(apiEndpoint, HttpMethod.GET, entity,  new ParameterizedTypeReference<List<Users>>() {});
            return processUsersList(responseEntity);
        } catch (Exception e) {
            log.error("Error occurred while fetching users for group ID {}", groupId, e);
            throw new RuntimeException("Error occurred while fetching users for group ID " + groupId, e);
        }
    }


    /**
     * Creates a user and assigns them to a group.
     *
     * @param user the user object to be created
     * @param accessToken the access token for authentication
     * @return the created user with updated group and user IDs
     */
    public Users createUser(Users user, String accessToken) {
        log.info("Creating user using API: {}", usersApiUrl);
        HttpEntity<Users> requestBody = APIUtils.getHttpEntity(user,accessToken);
        try {
            ResponseEntity<?> responseEntity = restTemplate.postForEntity(usersApiUrl, requestBody, Object.class);
            if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
                return handleUserCreation(user, responseEntity, accessToken);
            } else {
                log.error("Failed to create user. Status code: {}", responseEntity.getStatusCode().value());
                log.error("Response body: {}", responseEntity.getBody());
            }
        } catch (Exception e) {
            log.error("Error occurred while creating user", e);
        }
        return user;
    }

    /**
     * Provisions a user under a specific group.
     *
     * @param groupId the group ID
     * @param userId the user ID
     * @param accessToken the access token for authentication
     * @return true if provisioning is successful, otherwise false
     */
    public boolean provisionUserUnderAGroup(String groupId, String userId, String accessToken) {
        String apiEndpoint = usersApiUrl + userId + "/groups/" + groupId;
        log.info("Provisioning user under group using API: {}", apiEndpoint);
        try {
            HttpEntity<String> entity = new HttpEntity<>(APIUtils.buildHeaders(accessToken));
            ResponseEntity<?> responseEntity = restTemplate.exchange(apiEndpoint, HttpMethod.PUT, entity, ResponseEntity.class);

            return responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            log.error("Error occurred while provisioning user under group", e);
            return false;
        }
    }

    /**
     * De-provision a user from a specific group.
     *
     * @param groupId the group ID
     * @param userId the user ID
     * @param accessToken the access token for authentication
     * @return true if de-provisioning is successful, otherwise false
     */
    public boolean deProvisionUserUnderAGroup(String groupId, String userId, String accessToken) {
        String apiEndpoint = groupsApiUrl + userId + "/groups/" + groupId;
        log.info("De-provisioning user from group using API: {}", apiEndpoint);
        try {
            HttpEntity<String> entity = new HttpEntity<>(APIUtils.buildHeaders(accessToken));
            ResponseEntity<?> responseEntity = restTemplate.exchange(apiEndpoint, HttpMethod.DELETE, entity, ResponseEntity.class);
            return responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            log.error("Error occurred while de-provisioning user from group", e);
            return false;
        }
    }
    /**
     * Deletes a user by ID.
     *
     * @param userId The ID of the user to delete
     * @param accessToken Access token for authentication
     * @return True if the deletion was successful, false otherwise
     */
    public boolean deleteUserById(String userId, String accessToken) {
        String apiEndpoint = usersApiUrl  + userId;
        log.info("Deleting user using API: {}", apiEndpoint);
        try {
            HttpEntity<String> entity = new HttpEntity<>(APIUtils.buildHeaders(accessToken));
            ResponseEntity<?> responseEntity = restTemplate.exchange(apiEndpoint, HttpMethod.DELETE, entity, ResponseEntity.class);
            if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
                log.info("User deleted successfully");
                return true;
            } else {
                log.warn("User deletion failed with status code: {}", responseEntity.getStatusCode());
                throw new RuntimeException("Failed to delete user. Status code: " + responseEntity.getStatusCodeValue());
            }
        } catch (Exception e) {
            log.error("Error occurred while deleting user", e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    /**
     * Retrieves a user by user ID.
     *
     * @param userId The ID of the user to retrieve
     * @param accessToken Access token for authentication
     * @return The retrieved User object
     * @throws RuntimeException If there's an error fetching the user
     */
    public Users getUserById(String userId, String accessToken) throws RuntimeException {
        String apiEndpoint = usersApiUrl + userId;
        log.info("Retrieving user using API: {}", apiEndpoint);
        try {
            HttpEntity<String> entity = new HttpEntity<>(APIUtils.buildHeaders(accessToken));
            ResponseEntity<Users> responseEntity = restTemplate.exchange(apiEndpoint, HttpMethod.GET, entity, Users.class);

            int statusCode = responseEntity.getStatusCode().value();
            log.info("Received status code: {}", statusCode);

            if (responseEntity.getBody() != null && !responseEntity.getBody().toString().isEmpty()) {
                Users user = responseEntity.getBody();
                log.info("Successfully retrieved user: {}", userId);
                return user;
            } else {
                log.warn("No user found for ID: {}", userId);
                throw new RuntimeException("No user found");
            }
        } catch (Exception e) {
            log.error("Error occurred while retrieving user", e);
            //add log with userid
            throw new RuntimeException("Failed to retrieve user  User ID: {} . Cause: {}"+userId,e);
        }
    }

    private List<Users> processUsersList(ResponseEntity<?> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            List<Users> usersList = (List<Users>) responseEntity.getBody();
            log.info("Successfully fetched {} users", usersList.size());
            return usersList;
        } else {
            log.error("Failed to fetch users. Status code: {}", responseEntity.getStatusCode().value());
            throw new RuntimeException("Failed to fetch users. HTTP status: " + responseEntity.getStatusCode().value());
        }
    }

    private Users handleUserCreation(Users user, ResponseEntity<?> responseEntity, String accessToken) {
        HttpHeaders headers = responseEntity.getHeaders();
        Optional<String> location = Optional.ofNullable(headers.getFirst("Location"));
        if (location.isPresent()) {
            String userId = location.get().replace(usersApiUrl, "");
            log.info("User created successfully. User ID: {}, Location: {}", userId, location.get());
            String groupId = StringUtils.hasLength(user.getGroupId()) ? user.getGroupId() : propertyOwnersGroupId;
            if (provisionUserUnderAGroup(groupId, userId, accessToken)) {
                user.setGroupId(groupId);
                user.setUserId(userId);
                return user;
            }else {
                //delete user if provisioning fails
            }
        } else {
            log.warn("No Location header found in response");
        }
        return user;
    }

}
