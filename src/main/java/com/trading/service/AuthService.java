package com.trading.service;

import com.trading.config.JwtProvider;
import com.trading.entity.TwoFactorOTP;
import com.trading.entity.User;
import com.trading.repository.UserRepo;
import com.trading.utils.OTPUtils;
import com.trading.web.response.AuthResponse;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 10-08-2025 22:50
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final RequestValidator requestValidator;
    private final TwoFactorOTPService twoFactorOTPService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse registerUser(User registrationRequest) {
        if (userRepo.findByEmail(registrationRequest.getEmail()) != null) {
            throw new IllegalStateException("This email is already registered.");
        }

        User newUser = new User();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userRepo.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                registrationRequest.getEmail(),
                registrationRequest.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwtToken(jwtToken);
        authResponse.setStatus(true);
        authResponse.setMessage("User registered successfully!");

        return authResponse;
    }

    @Transactional
    public AuthResponse loginUser(User loginRequest) throws MessagingException {
        Authentication authentication = requestValidator.authenticate(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User authUser = userRepo.findByEmail(authentication.getName());

        if (authUser.getTwoFactorAuthentication() != null && authUser.getTwoFactorAuthentication().getIsEnabled()) {
            return initiateTwoFactorAuth(authUser, authentication);
        }

        String jwtToken = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwtToken(jwtToken);
        authResponse.setStatus(true);
        authResponse.setMessage("Logged in successfully!");
        return authResponse;
    }

    private AuthResponse initiateTwoFactorAuth(User user, Authentication authentication) throws MessagingException {
        String jwtToken = JwtProvider.generateToken(authentication);
        String otp = OTPUtils.generateOtp();

        TwoFactorOTP oldTwoFactorOTP = twoFactorOTPService.findByUser(user.getId());
        if (oldTwoFactorOTP != null) {
            twoFactorOTPService.deleteTwoFactorOTP(oldTwoFactorOTP);
        }
        TwoFactorOTP newTwoFactorOTP = twoFactorOTPService.createTwoFactorOTP(user, otp, jwtToken);

        emailService.sendVerificationOtpMail(user.getEmail(), otp);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Two-factor authentication is enabled. Please verify your identity.");
        authResponse.setIsTwoFactorAuthEnabled(true);
        authResponse.setSessionId(newTwoFactorOTP.getId());
        return authResponse;
    }

    public AuthResponse verify2FAOtp(String otp, String sessionId) {
        TwoFactorOTP twoFactorOTP = twoFactorOTPService.findById(sessionId);

        if (twoFactorOTPService.verifyTwoFactorOTP(twoFactorOTP, otp)) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Two-factor authentication is verified successfully.");
            authResponse.setJwtToken(twoFactorOTP.getJwtToken());
            authResponse.setStatus(true);
            twoFactorOTPService.deleteTwoFactorOTP(twoFactorOTP);
            return authResponse;
        }
        throw new RuntimeException("Invalid OTP!");
    }
}
