package com.acma.properties.exceptions;

import lombok.Getter;

@Getter
public class ProvisioningFailureException extends Exception {
    private final String groupId;
    private final String userId;

    public ProvisioningFailureException(String message, String groupId, String userId) {
        super(message);
        this.groupId = groupId;
        this.userId = userId;
    }
}