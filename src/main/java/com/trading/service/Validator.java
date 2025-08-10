package com.trading.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 08-08-2025 22:38
 */
@FunctionalInterface
public interface Validator<T> {
    /**
     *
     * @param argument
     * @throws BadCredentialsException
     */
    Authentication authenticate(T argument) throws BadCredentialsException;
}
