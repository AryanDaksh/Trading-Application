package com.trading.service;

import com.trading.entity.PaymentDetails;
import com.trading.entity.User;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 05-09-2025 22:33
 */
public interface PaymentDetailsService {
    public PaymentDetails addPaymentDetails(final String accountNumber, final String accountHolderName, final String ifscCode, final String bankName, final User user);
    public PaymentDetails getPaymentDetailsByUser(final User user);
}
