package com.acma.properties.controller;

import com.acma.properties.beans.UserBean;
import com.acma.properties.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserResource {

    private final UserService userService;

    private final HttpServletRequest httpServletRequest;

    @GetMapping("/")
    @Operation(description = "Retrieves all users" ,security = @SecurityRequirement( name = "bearerAuth" ))
    public ResponseEntity<List<UserBean>> getAllUsers(String accessToken) {
        log.info("Retrieving all users with access token: {}", getBearerToken());
        List<UserBean> allUsers = userService.getAllUsers(getBearerToken());
        if (!CollectionUtils.isEmpty( allUsers )) {
            log.info("Found {} users", allUsers.size());
            return ResponseEntity.ok(allUsers);
        } else {
            log.warn("No users found");
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("/agents")
    public ResponseEntity<List<UserBean>> getAllAgents() {
        return null;
    }

    @GetMapping("/brokers")
    public ResponseEntity<List<UserBean>> getAllBrokers() {
        return null;
    }

    @GetMapping("/owners")
    public ResponseEntity<List<UserBean>> getPropertyOwners() {
        return null;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserBean> getUserById(String userId) {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<UserBean> createUser(UserBean  userBean) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserBean> deleteUserById(String userId) {
        return null;
    }

    private String getBearerToken() {
        String bearerToken =httpServletRequest.getHeader( "Authorization" );
        if(StringUtils.hasText(bearerToken) && (StringUtils.hasText( "Bearer " ))) {
            bearerToken = StringUtils.replace( bearerToken,"Bearer ","" );
        }
        return bearerToken;
    }
}
