package com.acma.properties.advices;

import com.acma.properties.exceptions.ProvisioningFailureException;
import com.acma.properties.exceptions.UserCreationException;
import com.acma.properties.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class AcmaUserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message", ex.getMessage());
        responseBody.put("details", "User not found");

        log.error("User not found: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<Map<String, Object>> handleUserCreationException(UserCreationException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message", ex.getMessage());
        responseBody.put("details", "User creation failed");

        log.error("User creation failed: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProvisioningFailureException.class)
    public ResponseEntity<Map<String, Object>> handleProvisioningFailureException(ProvisioningFailureException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message", ex.getMessage());
        responseBody.put("details", "User provisioning failed");
        responseBody.put("groupId", ex.getGroupId());
        responseBody.put("userId", ex.getUserId());

        log.error("User provisioning failed: GroupId: {}, UserId: {}", ex.getGroupId(), ex.getUserId(), ex);
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message", "An unexpected error occurred");
        responseBody.put("details", ex.getMessage());

        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}