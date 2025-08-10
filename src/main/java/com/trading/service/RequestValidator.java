package com.trading.service;

import com.trading.entity.User;
import io.micrometer.core.instrument.config.validate.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 08-08-2025 22:39
 */
@Service
@RequiredArgsConstructor
public class RequestValidator implements Validator<User> {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(User user) throws BadCredentialsException {
        String email = user.getEmail();
        String password = user.getPassword();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        if(userDetails == null) {
            throw new BadCredentialsException("Invalid Username!");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password!");
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }
}
