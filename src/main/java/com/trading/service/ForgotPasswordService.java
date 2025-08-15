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
    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);
    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUser(Long userId);
    void deleteToken(ForgotPasswordToken token);
}
