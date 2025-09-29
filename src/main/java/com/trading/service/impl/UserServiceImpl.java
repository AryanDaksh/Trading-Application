package com.trading.service.impl;

import com.trading.config.JwtProvider;
import com.trading.repository.ForgotPasswordRepo;
import com.trading.web.request.ForgotPasswordTokenRequest;
import com.trading.entity.ForgotPasswordToken;
import com.trading.entity.TwoFactorAuthentication;
import com.trading.entity.User;
import com.trading.entity.VerificationCode;
import com.trading.enums.VerificationType;
import com.trading.repository.UserRepo;
import com.trading.service.EmailService;
import com.trading.service.ForgotPasswordService;
import com.trading.service.UserService;
import com.trading.service.VerificationCodeService;
import com.trading.utils.OTPUtils;
import com.trading.web.request.ResetPasswordRequest;
import com.trading.web.response.ApiResponse;
import com.trading.web.response.AuthResponse;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 10-08-2025 23:31
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;
    private final ForgotPasswordService forgotPasswordService;
    private final ForgotPasswordRepo forgotPasswordRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void sendVerificationOtp(final String jwtToken, final VerificationType verificationType) throws MessagingException {
        User user = findUserProfileByJwt(jwtToken);
        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
        if (verificationCode != null) {
            verificationCodeService.deleteVerificationCode(verificationCode);
        }
        verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        if (verificationType.equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpMail(user.getEmail(), verificationCode.getOtp());
        }
    }

    @Override
    public User verifyOtpAndEnableTwoFactor(final String jwtToken, final String otp) {
        User user = findUserProfileByJwt(jwtToken);
        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
        if (verificationCode == null) {
            throw new IllegalStateException("No OTP found. Please request a new one.");
        }
        if (!verificationCode.getOtp().equals(otp)) {
            throw new IllegalStateException("Invalid OTP provided for two-factor authentication.");
        }
        User updatedUser = enableTwoFactorAuth(verificationCode.getVerificationType(), user);
        verificationCodeService.deleteVerificationCode(verificationCode);
        return updatedUser;
    }

    @Override
    public AuthResponse sendForgotPasswordOtp(final String jwtToken, final ForgotPasswordTokenRequest forgotPasswordTokenRequest) throws MessagingException {
        User user = findByEmail(forgotPasswordTokenRequest.getSendTo());
        String otp = OTPUtils.generateOtp();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findByUser(user.getId());
        if (forgotPasswordToken == null) {
            forgotPasswordToken = forgotPasswordService.createToken(user, id, otp, forgotPasswordTokenRequest.getVerificationType(), forgotPasswordTokenRequest.getSendTo());
        }
        if (forgotPasswordTokenRequest.getVerificationType() == VerificationType.EMAIL) {
            emailService.sendVerificationOtpMail(user.getEmail(), forgotPasswordToken.getOtp());
        }
        AuthResponse authResponse = new AuthResponse();
        authResponse.setSessionId(forgotPasswordToken.getId());
        authResponse.setMessage("Forgot Password OTP sent successfully!");
        return authResponse;
    }

    @Override
    public ApiResponse resetPassword(final String id, final ResetPasswordRequest resetPasswordRequest) {
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);
        if (forgotPasswordToken.getExpirationTime().isBefore(Instant.now())) {
            forgotPasswordRepo.delete(forgotPasswordToken);
            throw new IllegalStateException("This password reset token has expired.");
        }
        boolean isVerified = forgotPasswordToken.getOtp().equals(resetPasswordRequest.getOtp());
        if (isVerified) {
            updatePassword(forgotPasswordToken.getUser(), resetPasswordRequest.getPassword());
            forgotPasswordRepo.delete(forgotPasswordToken);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Password reset successfully.");
            return apiResponse;
        }
        throw new IllegalStateException("Invalid OTP provided for password reset.");
    }

    public User findByEmail(final String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new IllegalStateException("User not found with the provided JWT token.");
        }
        return user;
    }

    @Override
    public User findUserProfileByJwt(final String jwtToken) {
        String email = JwtProvider.getEmailFromToken(jwtToken);
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new IllegalStateException("User not found with the provided JWT token.");
        }
        return user;
    }

    public User findById(final Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isEmpty()){
            throw new IllegalStateException("User not found with the provided ID.");
        }
        return null;
    }

    public User enableTwoFactorAuth(final VerificationType verificationType, final User user) {
        TwoFactorAuthentication twoFactorAuthentication = new TwoFactorAuthentication();
        twoFactorAuthentication.setIsEnabled(true);
        twoFactorAuthentication.setSendTo(verificationType);

        user.setTwoFactorAuthentication(twoFactorAuthentication);
        return userRepo.save(user);
    }

    public void updatePassword(final User user, final String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }
}
