package com.trading.service;

import com.trading.entity.ForgotPasswordToken;
import com.trading.entity.User;
import com.trading.enums.VerificationType;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 12-08-2025 20:48
 */
public interface ForgotPasswordService {
    ForgotPasswordToken createToken(final User user, final String id, final String otp, final VerificationType verificationType, final String sendTo);
    ForgotPasswordToken findById(final String id);
    ForgotPasswordToken findByUser(final Long userId);
    void deleteToken(final ForgotPasswordToken token);
}
