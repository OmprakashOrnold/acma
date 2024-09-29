package com.acma.properties.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-09-17
 */
@Data
@Builder
@AllArgsConstructor
public class UserAccess implements Serializable {

    private static final long serialVersionUID = -2191932799406119458L;

    @Builder.Default
    private boolean manageGroupMembership = true;
    @Builder.Default
    private boolean view = true;
    @Builder.Default
    private boolean mapRoles = true;
    @Builder.Default
    private boolean impersonate = true;
    @Builder.Default
    private boolean manage = true;

}
