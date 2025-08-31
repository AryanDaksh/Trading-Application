package com.trading.entity;

import com.trading.enums.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 12-08-2025 20:48
 */
@Data
@Entity
public class ForgotPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @OneToOne
    private User user;
    private String otp;
    private VerificationType verificationType;
    private String sendTo;
    private Instant expirationTime;
}
