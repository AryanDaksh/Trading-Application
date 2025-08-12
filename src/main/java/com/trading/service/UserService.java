package com.trading.service;

import com.trading.entity.User;
import com.trading.enums.VERIFICATION_TYPE;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 10-08-2025 23:28
 */
public interface UserService {
    public User findByEmail(String email);
    public User findByJwt(String jwtToken);
    public User findById(Long userId);
    public User enableTwoFactorAuth(VERIFICATION_TYPE verificationType, String sendTo ,User user);
    public User updatePassword(User user, String newPassword);
}
