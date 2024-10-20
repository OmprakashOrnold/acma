package com.acma.properties.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}