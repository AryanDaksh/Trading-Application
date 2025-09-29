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
    void sendVerificationOtp(final String jwtToken, final VerificationType verificationType) throws MessagingException;
    User verifyOtpAndEnableTwoFactor(final String jwtToken, final String otp);
    AuthResponse sendForgotPasswordOtp(final String jwtToken, final ForgotPasswordTokenRequest forgotPasswordTokenRequest) throws MessagingException;
    ApiResponse resetPassword(final String id, final ResetPasswordRequest resetPasswordRequest);
    User findUserProfileByJwt(final String jwtToken);
}
