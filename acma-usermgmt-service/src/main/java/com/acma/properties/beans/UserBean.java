package com.acma.properties.beans;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-09-28
 */
@Data
@Builder
public class UserBean implements Serializable {

    private static final long serialVersionUID = 526485415768575666L;

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    @Builder.Default
    private boolean enabled = false;
    @Builder.Default
    private boolean totp = false;
    @Builder.Default
    private boolean emailVerified = false;
}
