package com.acma.properties.models;

import lombok.*;

import java.util.List;

/**
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-09-18
 */
@Data
@Builder
public class UsersResponse {

    private List<Users> usersList;
}
