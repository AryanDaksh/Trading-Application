package com.trading.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 09-08-2025 17:48
 */
@Entity
@Data
public class TwoFactorOTP {
    @Id
    private String id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    private User user;
    private String otpCode;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String jwtToken;
    private LocalDateTime expiryTime;
    private int attempts = 0;
}
