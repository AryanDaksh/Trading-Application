package com.trading.web.request;

import com.trading.enums.VerificationType;
import lombok.Data;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 12-08-2025 21:05
 */
@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
