package com.trading.web.controller;

import com.trading.entity.User;
import com.trading.service.AuthService;
import com.trading.web.response.AuthResponse;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 08-08-2025 01:28
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(final @RequestBody User user) {
        AuthResponse response = authService.registerUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(final @RequestBody User user) throws MessagingException {
        AuthResponse response = authService.loginUser(user);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifyTwoFactorAuth(final @RequestParam("otp") String otp, final @RequestParam("sessionId") String sessionId) {
        AuthResponse response = authService.verify2FAOtp(otp, sessionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
