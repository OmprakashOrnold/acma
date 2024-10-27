package com.acma.properties.resources;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class TokenProcessResource {

    @Autowired
    private  OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping("/token")
    public Map<String, String> generateToken(OAuth2AuthenticationToken auth2AuthenticationToken) {

        OAuth2AuthorizedClient auth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient( auth2AuthenticationToken.getAuthorizedClientRegistrationId(), auth2AuthenticationToken.getName() );

        String accessToken = auth2AuthorizedClient.getAccessToken().getTokenValue();
        log.info( "access token " + accessToken );

        OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info( "id token " + user.getIdToken().getTokenValue() ) ;

        Map<String, String> userMap = new HashMap<>();
        userMap.put( "access_token", accessToken );
        userMap.put( "refresh_token",  auth2AuthorizedClient.getAccessToken().getTokenValue() );
        userMap.put( "id_token", user.getIdToken().getTokenValue() );


        return userMap;
    }


}
