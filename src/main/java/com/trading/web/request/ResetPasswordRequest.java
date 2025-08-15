package com.trading.web.request;

import lombok.Data;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 15-08-2025 12:18
 */
@Data
public class ResetPasswordRequest {
    private String otp;
    private String password;
}
