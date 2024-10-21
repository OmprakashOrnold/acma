package com.acma.properties.exceptions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsersExceptionsDisplayBean {

    private String message;
    private int code;

}
