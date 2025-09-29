package com.trading.service;

import com.trading.entity.TwoFactorOTP;
import com.trading.entity.User;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 09-08-2025 17:51
 */

public interface TwoFactorOTPService {
    TwoFactorOTP createTwoFactorOTP(final User user, final String otpCode, final String jwtToken);
    TwoFactorOTP findByUser(final Long userId);
    TwoFactorOTP findById(final String id);
    boolean verifyTwoFactorOTP(final TwoFactorOTP twoFactorOTP, final String otpCode);
    void deleteTwoFactorOTP(final TwoFactorOTP twoFactorOTP);
}
