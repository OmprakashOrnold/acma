package com.acma.properties.controller;

import com.acma.properties.beans.UserBean;
import com.acma.properties.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.acma.properties.utility.APIUtils.getBearerToken;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserResource {

    private final UserService userService;
    private final HttpServletRequest httpServletRequest;

    @GetMapping("/")
    @Operation(description = "Retrieves all users", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<UserBean>> getAllUsers() {
        log.info("Retrieving all users with access token: {}", getBearerToken(this.httpServletRequest));
        List<UserBean> allUsers = userService.getAllUsers(getBearerToken(this.httpServletRequest));

        if (!CollectionUtils.isEmpty(allUsers)) {
            log.info("Found {} users", allUsers.size());
            return ResponseEntity.ok().body(allUsers);
        } else {
            log.warn("No users found");
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/agents")
    @Operation(description = "Retrieves all agents", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<UserBean>> getAllAgents(String agentsId) {
        log.info("Retrieving all agents with access token: {}", getBearerToken(this.httpServletRequest));
        List<UserBean> agents = userService.getUsersByGroupId(agentsId, getBearerToken(this.httpServletRequest));

        if (!CollectionUtils.isEmpty(agents)) {
            log.info("Found {} agents", agents.size());
            return ResponseEntity.ok().body(agents);
        } else {
            log.warn("No agents found");
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/brokers")
    @Operation(description = "Retrieves all brokers", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<UserBean>> getAllBrokers(String brokersId) {
        log.info("Retrieving all brokers with access token: {}", getBearerToken(this.httpServletRequest));
        List<UserBean> brokers = userService.getUsersByGroupId(brokersId, getBearerToken(this.httpServletRequest));

        if (!CollectionUtils.isEmpty(brokers)) {
            log.info("Found {} brokers", brokers.size());
            return ResponseEntity.ok().body(brokers);
        } else {
            log.warn("No brokers found");
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/owners")
    @Operation(description = "Retrieves all owners", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<UserBean>> getPropertyOwners(String ownersId) {
        log.info("Retrieving all owners with access token: {}", getBearerToken(this.httpServletRequest));
        List<UserBean> owners = userService.getUsersByGroupId(ownersId, getBearerToken(this.httpServletRequest));

        if (!CollectionUtils.isEmpty(owners)) {
            log.info("Found {} owners", owners.size());
            return ResponseEntity.ok().body(owners);
        } else {
            log.warn("No owners found");
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{userId}")
    @Operation(description = "Get User Details By ID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserBean> getUserById(@PathVariable String userId) {
        log.info("Retrieving user with id: {} and access token: {}", userId, getBearerToken(this.httpServletRequest));
        UserBean userBean = userService.getUserById(userId, getBearerToken(this.httpServletRequest));

        if (ObjectUtils.isEmpty(userBean)) {
            log.warn("User not found with id: {}", userId);
            return ResponseEntity.notFound().build();
        } else {
            log.info("Found user with id: {}", userId);
            return ResponseEntity.ok().body(userBean);
        }
    }

    @PostMapping("/")
    @Operation(description = "Creating User", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserBean> createUser(@Valid @RequestBody UserBean userBean) {
        log.info("Creating user with access token: {} and userBean: {}, ", getBearerToken(this.httpServletRequest), userBean);
        UserBean createdUser = userService.createUser(userBean, getBearerToken(this.httpServletRequest));

        if (ObjectUtils.isEmpty(createdUser)) {
            log.warn("Failed to create user with email: {}", userBean.getEmail());
            return ResponseEntity.badRequest().build();
        } else {
            log.info("Created user with email: {}", createdUser.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }
    }

    @DeleteMapping("/{userId}")
    @Operation(description = "Deleting User", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> deleteUserById(@PathVariable String userId) {
        log.info("Deleting user with id: {} and access token: {}", userId, getBearerToken(this.httpServletRequest));
        userService.deleteUser(userId, getBearerToken(this.httpServletRequest));
        return ResponseEntity.noContent().build();
    }
}
