package com.trading.service;

import com.trading.entity.User;
import com.trading.entity.VerificationCode;
import com.trading.enums.VerificationType;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 12-08-2025 15:41
 */

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);
    VerificationCode getVerificationCodeById(Long id);
    VerificationCode getVerificationCodeByUser(Long userId);
    void deleteVerificationCode(VerificationCode verificationCode);
}
