package com.trading.entity;

import com.trading.enums.VerificationType;
import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 08-08-2025 01:18
 */
@Data
@Embeddable
public class TwoFactorAuthentication {
    private Boolean isEnabled = false;
    private VerificationType sendTo;
}
