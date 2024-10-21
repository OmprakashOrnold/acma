package com.acma.properties.beans;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-09-28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBean implements Serializable {

    private static final long serialVersionUID = 526485415768575666L;

    @NotEmpty(message = "{username.notEmpty.message}")
    @Size(min = 3,max = 255, message = "{username.size.message}")
    private String username;

    @NotEmpty(message = "{firstName.notEmpty.message}")
    @Size(max = 255, message = "{firstName.size.message}")
    private String firstName;

    @NotEmpty(message = "{lastName.notEmpty.message}")
    @Size(max = 255, message = "{lastName.size.message}")
    private String lastName;

    @NotEmpty(message = "{email.notEmpty.message}")
    @Size(max = 255, message = "${email.size.message}")
    @Pattern( regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "{email.invalid.message}")
    private String email;

    @Builder.Default
    private boolean enabled = false;
    @Builder.Default
    private boolean totp = false;
    @Builder.Default
    private boolean emailVerified = false;
    private String id;

    @NotEmpty(message = "{mobile.notEmpty.message}")
    @Pattern( regexp = "^[0-9]{10}$", message = "{mobile.invalid.message}")
    private String mobile;
}
