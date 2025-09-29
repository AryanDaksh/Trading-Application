package com.trading.service.impl;

import com.trading.entity.TwoFactorOTP;
import com.trading.entity.User;
import com.trading.repository.TwoFactorOTPRepo;
import com.trading.service.TwoFactorOTPService;
import com.trading.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.trading.utils.AppConstants.MAX_OTP_ATTEMPTS;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 09-08-2025 17:56
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TwoFactorOTPServiceImpl implements TwoFactorOTPService {
    private final TwoFactorOTPRepo twoFactorOTPRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TwoFactorOTP createTwoFactorOTP(final User user, final String otpCode, final String jwtToken) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
        twoFactorOTP.setId(id);
        twoFactorOTP.setUser(user);
        twoFactorOTP.setOtpCode(passwordEncoder.encode(otpCode));
        twoFactorOTP.setJwtToken(jwtToken);
        twoFactorOTP.setExpiryTime(DateUtils.getCurrentDateTime().plusMinutes(5));

        return twoFactorOTPRepo.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP findByUser(final Long userId) {
        return twoFactorOTPRepo.findByUserId(userId);
    }

    @Override
    public TwoFactorOTP findById(final String id) {
        Optional<TwoFactorOTP> optional = twoFactorOTPRepo.findById(id);
        return optional.orElse(null);
    }

    @Override
    public boolean verifyTwoFactorOTP(final TwoFactorOTP twoFactorOTP, final String otpCode) {
        if (twoFactorOTP.getExpiryTime().isBefore(DateUtils.getCurrentDateTime())) {
            log.warn("OTP expired. Please request a new OTP.");
            twoFactorOTPRepo.delete(twoFactorOTP);
            return false;
        }
        if (twoFactorOTP.getAttempts() >= MAX_OTP_ATTEMPTS) {
            log.warn("Maximum number of incorrect attempts reached. Try again later.");
            twoFactorOTPRepo.delete(twoFactorOTP);
            return false;
        }
        boolean isValid = passwordEncoder.matches(otpCode, twoFactorOTP.getOtpCode());
        if (!isValid) {
            twoFactorOTP.setAttempts(twoFactorOTP.getAttempts() + 1);
            twoFactorOTPRepo.save(twoFactorOTP);
            return false;
        }
        twoFactorOTPRepo.delete(twoFactorOTP);
        return true;
    }


    @Override
    public void deleteTwoFactorOTP(final TwoFactorOTP twoFactorOTP) {
        twoFactorOTPRepo.delete(twoFactorOTP);
    }
}
