package com.trading.service.impl;

import com.trading.config.JwtProvider;
import com.trading.entity.TwoFactorAuthentication;
import com.trading.entity.User;
import com.trading.enums.VERIFICATION_TYPE;
import com.trading.repository.UserRepo;
import com.trading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 10-08-2025 23:31
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public User findByEmail(String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new IllegalStateException("User not found with the provided JWT token.");
        }
        return user;    }

    @Override
    public User findByJwt(String jwtToken) {
        String email = JwtProvider.getEmailFromToken(jwtToken);
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new IllegalStateException("User not found with the provided JWT token.");
        }
        return user;
    }

    @Override
    public User findById(Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isEmpty()){
            throw new IllegalStateException("User not found with the provided ID.");
        }
        return null;
    }

    @Override
    public User enableTwoFactorAuth(VERIFICATION_TYPE verificationType, String sendTo, User user) {
        TwoFactorAuthentication twoFactorAuthentication = new TwoFactorAuthentication();
        twoFactorAuthentication.setIsEnabled(true);
        twoFactorAuthentication.setSendTo(verificationType);

        user.setTwoFactorAuthentication(twoFactorAuthentication);
        return userRepo.save(user);
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepo.save(user);
    }
}
