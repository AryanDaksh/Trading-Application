package com.trading.service.impl;

import com.trading.entity.ForgotPasswordToken;
import com.trading.entity.User;
import com.trading.enums.VerificationType;
import com.trading.repository.ForgotPasswordRepo;
import com.trading.service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 12-08-2025 20:52
 */
@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final ForgotPasswordRepo forgotPasswordRepo;

    @Override
    public ForgotPasswordToken createToken(final User user, final String id, final String otp, final VerificationType verificationType, final String sendTo) {
        ForgotPasswordToken token = new ForgotPasswordToken();
        token.setUser(user);
        token.setOtp(otp);
        token.setVerificationType(verificationType);
        token.setSendTo(sendTo);
        token.setId(id);
        token.setExpirationTime(Instant.now().plusSeconds(600));
        return forgotPasswordRepo.save(token);
    }

    @Override
    public ForgotPasswordToken findById(final String id) {
        Optional<ForgotPasswordToken> token = forgotPasswordRepo.findById(id);
        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(final Long userId) {
        return forgotPasswordRepo.findByUserId(userId);
    }

    @Override
    public void deleteToken(final ForgotPasswordToken token) {
        forgotPasswordRepo.delete(token);
    }
}
