package com.trading.web.controller;

import com.trading.config.JwtProvider;
import com.trading.entity.TwoFactorOTP;
import com.trading.entity.User;
import com.trading.repository.UserRepo;
import com.trading.service.EmailService;
import com.trading.service.RequestValidator;
import com.trading.service.TwoFactorOTPService;
import com.trading.utils.OTPUtils;
import com.trading.web.response.AuthResponse;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 08-08-2025 01:28
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserRepo userRepo;
    private final RequestValidator requestValidator;
    private final PasswordEncoder passwordEncoder;
    private final TwoFactorOTPService twoFactorOTPService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        User isEmailExists = userRepo.findByEmail(user.getEmail());
        if (isEmailExists != null) {
            throw new RuntimeException("This email is already registered with an account. Please try with a different email.");
        }

        User newUser =new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        userRepo.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwtToken(jwtToken);
        authResponse.setStatus(true);
        authResponse.setMessage("User registered successfully!");

        return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody User user) throws MessagingException {
        Authentication authentication = requestValidator.authenticate(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = JwtProvider.generateToken(authentication);
        User authUser = userRepo.findByEmail(user.getUsername());

        if (user.getTwoFactorAuthentication().getIsEnabled()) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Two-factor authentication is enabled. Please verify your identity.");
            authResponse.setIsTwoFactorAuthEnabled(true);
            String otp = OTPUtils.generateOtp();

            TwoFactorOTP oldTwoFactorOTP = twoFactorOTPService.findByUser(authUser.getId());
            if (oldTwoFactorOTP != null) {
                twoFactorOTPService.deleteTwoFactorOTP(oldTwoFactorOTP);
            }

            TwoFactorOTP newTwoFactorOTP = twoFactorOTPService.createTwoFactorOTP(authUser, otp, jwtToken);

            emailService.sendVerificationOtpMail(authUser.getEmail(), otp);

            authResponse.setSessionId(newTwoFactorOTP.getId());
            return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwtToken(jwtToken);
        authResponse.setStatus(true);
        authResponse.setMessage("Logged in successfully!");

        return new ResponseEntity<>(authResponse,HttpStatus.ACCEPTED);
    }
}
