package com.trading.utils;

import java.util.Random;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 09-08-2025 18:30
 */
public class OTPUtils {
    public static String generateOtp() {
        int otpLength = 6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            int digit = random.nextInt(10);
            otp.append(digit);
        }
        return otp.toString();
    }
}
