package com.acma.properties.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-09-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {

    private static final long serialVersionUID = 954575170870994446L;

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
    @JsonIgnore
    private String groupId;
    private String id;
    private List<String> realmRoles ;
    private UserAccess access;


}
