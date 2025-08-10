package com.trading.web.response;

import lombok.Data;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 08-08-2025 22:20
 */
@Data
public class AuthResponse {
    private String jwtToken;
    private Boolean status;
    private String message;
    private Boolean isTwoFactorAuthEnabled;
    private String sessionId;
}
