package com.trading.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trading.enums.USER_ROLE;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 08-08-2025 01:11
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private USER_ROLE role = USER_ROLE.ROLE_USER;
    private TwoFactorAuthentication twoFactorAuthentication = new TwoFactorAuthentication();
}
