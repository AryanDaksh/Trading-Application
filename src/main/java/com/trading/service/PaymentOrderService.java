package com.trading.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
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
    PaymentOrder createPaymentOrder(final User user, final Long amount, final PaymentGateway paymentGateway);
    PaymentOrder getPaymentOrderById(final Long id) throws Exception;
    Boolean approvePaymentOrder(final PaymentOrder paymentOrder, final String paymentId) throws RazorpayException;
    PaymentResponse createRazorpayOrder(final User user, final Long amount) throws RazorpayException;
    PaymentResponse createStripeOrder(final User user, final Long amount, final Long orderId) throws StripeException;
}
