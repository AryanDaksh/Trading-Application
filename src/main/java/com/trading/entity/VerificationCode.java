package com.trading.entity;

import com.trading.enums.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 12-08-2025 15:37
 */
@Data
@Entity
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String otp;
    @OneToOne
    private User user;
    private String email;
    private String mobile;
    private VerificationType verificationType;
}
