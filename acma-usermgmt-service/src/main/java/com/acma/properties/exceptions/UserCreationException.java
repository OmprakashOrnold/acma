package com.acma.properties.exceptions;

import lombok.Getter;

@Getter
public class UserCreationException extends RuntimeException {
    public UserCreationException(String message) {
        super(message);
    }
}