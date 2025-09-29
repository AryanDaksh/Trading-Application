package com.trading.service.impl;

import com.trading.entity.User;
import com.trading.entity.VerificationCode;
import com.trading.enums.VerificationType;
import com.trading.repository.VerificationCodeRepo;
import com.trading.service.VerificationCodeService;
import com.trading.utils.OTPUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 12-08-2025 15:42
 */
@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {
    private final VerificationCodeRepo verificationCodeRepo;

    @Override
    public VerificationCode sendVerificationCode(final User user, final VerificationType verificationType) {
        VerificationCode code = new VerificationCode();
        code.setOtp(OTPUtils.generateOtp());
        code.setVerificationType(verificationType);
        code.setUser(user);

        return verificationCodeRepo.save(code);
    }

    @Override
    public VerificationCode getVerificationCodeById(final Long id) {
        Optional<VerificationCode> verificationCode = verificationCodeRepo.findById(id);
        if (verificationCode.isPresent()) {
            return verificationCode.get();
        }
        throw new IllegalStateException("Verification code not found!");
    }

    @Override
    public VerificationCode getVerificationCodeByUser(final Long userId) {
        return verificationCodeRepo.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCode(final VerificationCode verificationCode) {
        verificationCodeRepo.delete(verificationCode);
    }
}
