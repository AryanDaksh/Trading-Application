package com.trading.service;

import com.trading.entity.TwoFactorOTP;
import com.trading.entity.User;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 09-08-2025 17:51
 */

public interface TwoFactorOTPService {
    TwoFactorOTP createTwoFactorOTP(User user, String otpCode, String jwtToken);
    TwoFactorOTP findByUser(Long userId);
    TwoFactorOTP findById(String id);
    boolean verifyTwoFactorOTP(TwoFactorOTP twoFactorOTP, String otpCode);
    void deleteTwoFactorOTP(TwoFactorOTP twoFactorOTP);
}
