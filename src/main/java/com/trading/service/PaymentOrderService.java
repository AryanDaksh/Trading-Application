package com.trading.service;

import com.razorpay.RazorpayException;
import com.trading.entity.PaymentOrder;
import com.trading.entity.User;
import com.trading.enums.PaymentGateway;
import com.trading.web.response.PaymentResponse;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 05-09-2025 22:49
 */
public interface PaymentOrderService {
    PaymentOrder createPaymentOrder(User user, Long amount, PaymentGateway paymentGateway);
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    Boolean approvePaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;
    PaymentResponse createRazorpayOrder(User user, Long amount) throws RazorpayException;
    PaymentResponse createStripeOrder(User user, Long amount);
}
