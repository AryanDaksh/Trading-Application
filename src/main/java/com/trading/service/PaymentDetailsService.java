package com.trading.service;

import com.trading.entity.PaymentDetails;
import com.trading.entity.User;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 05-09-2025 22:33
 */
public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifscCode, String bankName, User user);
    public PaymentDetails getPaymentDetailsByUser(User user);
}
