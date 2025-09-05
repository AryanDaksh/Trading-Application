package com.trading.service.impl;

import com.trading.entity.PaymentDetails;
import com.trading.entity.User;
import com.trading.repository.PaymentDetailsRepo;
import com.trading.service.PaymentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 05-09-2025 22:33
 */
@RequiredArgsConstructor
@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {
    private final PaymentDetailsRepo paymentDetailsRepo;

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifscCode, String bankName, User user) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIfscCode(ifscCode);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);
        return paymentDetailsRepo.save(paymentDetails);
    }

    @Override
    public PaymentDetails getPaymentDetailsByUser(User user) {
        return paymentDetailsRepo.findByUserId(user.getId());
    }
}
