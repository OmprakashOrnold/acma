package com.acma.properties.resources;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TokenProcessResource {

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    private final RedisTemplate<String, String> acmaCacheServer;

    private final HttpServletResponse response;

    private final HttpServletRequest request;

    /**
     * Generates an access token, refresh token, and ID token based on the provided OAuth2 authentication token.
     *
     * @param auth2AuthenticationToken The OAuth2 authentication token.
     * @return A map containing the generated tokens and token details.
     */
    @GetMapping("/token")
    public Map<String, String> generateToken(OAuth2AuthenticationToken auth2AuthenticationToken) {
        log.info( "Received OAuth2 authentication token" );

        String subjectId = getSubjectId( auth2AuthenticationToken );
        log.info( "Logged User id: {}", subjectId );

        OAuth2AuthorizedClient auth2AuthorizedClient = loadAuthorizedClient( auth2AuthenticationToken );
        log.info( "Loaded authorized client" );

        String accessToken = auth2AuthorizedClient.getAccessToken().getTokenValue();
        log.info( "Access token obtained: {}", accessToken );

        OidcUser user = getUserDetails();
        log.info( "ID token obtained: {}", user.getIdToken().getTokenValue() );

        Map<String, String> userMap = createUserMap( accessToken, auth2AuthorizedClient, user );
        log.info( "User details prepared: {}", userMap );

        saveToRedis( userMap, subjectId );
        log.info( "User details saved to Redis cache" );

        setCookie( userMap, auth2AuthenticationToken );
        log.info( "Cookie set for user session" );

        return userMap;
    }

    private String getSubjectId(OAuth2AuthenticationToken auth2AuthenticationToken) {
        return auth2AuthenticationToken.getName();
    }

    private OAuth2AuthorizedClient loadAuthorizedClient(OAuth2AuthenticationToken auth2AuthenticationToken) {
        return oAuth2AuthorizedClientService.loadAuthorizedClient( auth2AuthenticationToken.getAuthorizedClientRegistrationId(), getSubjectId( auth2AuthenticationToken ) );
    }

    private OidcUser getUserDetails() {
        return (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Map<String, String> createUserMap(String accessToken, OAuth2AuthorizedClient auth2AuthorizedClient, OidcUser user) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put( "access_token", accessToken );
        userMap.put( "refresh_token", auth2AuthorizedClient.getAccessToken().getTokenValue() );
        userMap.put( "id_token", user.getIdToken().getTokenValue() );
        userMap.put( "token_type", auth2AuthorizedClient.getAccessToken().getTokenType().getValue() );
        userMap.put( "token_expiry", auth2AuthorizedClient.getAccessToken().getExpiresAt().toString() );
        return userMap;
    }

    private void saveToRedis(Map<String, String> userMap, String subjectId) {
        JSONObject jsonObject = new JSONObject( userMap );
        acmaCacheServer.opsForHash().put( subjectId, subjectId, jsonObject.toString() );
    }

    private void setCookie(Map<String, String> userMap, OAuth2AuthenticationToken auth2AuthenticationToken) {
        String hostname = request.getServerName();
        String contextPath = request.getContextPath();

        Cookie cookie = new Cookie( "AcmaCk", Base64.getEncoder().encodeToString( userMap.toString().getBytes() ) );
        cookie.setPath( contextPath );
        cookie.setMaxAge( getMaxAge( auth2AuthenticationToken ) );
        cookie.setDomain( hostname );

        try {
            response.addCookie( cookie );
            response.flushBuffer();
        } catch (IOException e) {
            log.error( "Error setting cookie", e );
        }
    }

    private int getMaxAge(OAuth2AuthenticationToken auth2AuthenticationToken) {
        OAuth2AuthorizedClient auth2AuthorizedClient = loadAuthorizedClient( auth2AuthenticationToken );
        return auth2AuthorizedClient.getAccessToken().getExpiresAt().getNano();
    }
}
