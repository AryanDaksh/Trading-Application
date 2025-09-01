package com.trading.web.controller;

import com.trading.web.request.ForgotPasswordTokenRequest;
import com.trading.entity.User;
import com.trading.enums.VerificationType;
import com.trading.service.UserService;
import com.trading.web.request.ResetPasswordRequest;
import com.trading.web.response.ApiResponse;
import com.trading.web.response.AuthResponse;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 10-08-2025 23:42
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwtToken) {
        User user = userService.findUserProfileByJwt(jwtToken);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwtToken, @PathVariable VerificationType verificationType) throws MessagingException {
        userService.sendVerificationOtp(jwtToken, verificationType);
        return new ResponseEntity<>("Verification OTP sent successfully!", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuth(@PathVariable String otp, @RequestHeader("Authorization") String jwtToken) {
        User updatedUser = userService.verifyOtpAndEnableTwoFactor(jwtToken, otp);
        return new  ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/api/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(@RequestHeader("Authorization") String jwtToken, @RequestBody ForgotPasswordTokenRequest forgotPasswordTokenRequest) throws MessagingException {
        AuthResponse authResponse = userService.sendForgotPasswordOtp(jwtToken, forgotPasswordTokenRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PatchMapping("/api/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        ApiResponse apiResponse = userService.resetPassword(id, resetPasswordRequest);
        return new  ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
