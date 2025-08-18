package com.trading.service;

import com.trading.web.request.ForgotPasswordTokenRequest;
import com.trading.entity.User;
import com.trading.enums.VerificationType;
import com.trading.web.request.ResetPasswordRequest;
import com.trading.web.response.ApiResponse;
import com.trading.web.response.AuthResponse;
import jakarta.mail.MessagingException;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 10-08-2025 23:28
 */
public interface UserService {
    void sendVerificationOtp(String jwtToken, VerificationType verificationType) throws MessagingException;
    User verifyOtpAndEnableTwoFactor(String jwtToken, String otp);
    AuthResponse sendForgotPasswordOtp(String jwtToken, ForgotPasswordTokenRequest forgotPasswordTokenRequest) throws MessagingException;
    ApiResponse resetPassword(String id, ResetPasswordRequest resetPasswordRequest);
    User findByJwt(String jwtToken);
}
