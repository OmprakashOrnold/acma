package com.acma.properties.utility;

import com.acma.properties.models.Users;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

public class APIUtils {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;


    /**
     * Builds HttpEntity for API requests with user data and authorization headers.
     *
     * @param user the user data to be sent in the request body
     * @param accessToken the access token for authorization
     * @return HttpEntity containing the user object and headers
     */
    public static HttpEntity<Users> getHttpEntity(Users user, String accessToken) {
        // Reuse the buildHeaders method to create headers for this request
        MultiValueMap<String, String> headers = buildHeaders(accessToken);
        return new HttpEntity<>(user, headers);
    }

    /**
     * Builds headers for API requests.
     *
     * @param accessToken the access token for authorization
     * @return MultiValueMap containing headers
     */
    public static MultiValueMap<String, String> buildHeaders(String accessToken) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(AUTHORIZATION_HEADER, BEARER_TOKEN_PREFIX + accessToken);
        headers.add(CONTENT_TYPE_HEADER, APPLICATION_JSON);
        return headers;
    }

    public static String getBearerToken(HttpServletRequest httpServletRequest) {
        String bearerToken =httpServletRequest.getHeader( AUTHORIZATION_HEADER );
        if(StringUtils.hasText( bearerToken ) && bearerToken.startsWith( BEARER_TOKEN_PREFIX )) {
            bearerToken = bearerToken.substring( BEARER_TOKEN_PREFIX.length() );
        }
        return bearerToken;
    }
}