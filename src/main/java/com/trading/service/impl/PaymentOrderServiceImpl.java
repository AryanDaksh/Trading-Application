package com.trading.service.impl;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.trading.entity.PaymentOrder;
import com.trading.entity.User;
import com.trading.enums.PaymentGateway;
import com.trading.enums.PaymentOrderStatus;
import com.trading.repository.PaymentOrderRepo;
import com.trading.service.PaymentOrderService;
import com.trading.web.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 05-09-2025 22:49
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {
    private final PaymentOrderRepo paymentOrderRepo;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;
    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret;
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Override
    public PaymentOrder createPaymentOrder(final User user, final Long amount, final PaymentGateway paymentGateway) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentGateway(paymentGateway);
        return paymentOrderRepo.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(final Long id) throws Exception {
        return paymentOrderRepo.findById(id).orElseThrow(() -> new Exception("Payment Order not found!"));
    }

    @Override
    public Boolean approvePaymentOrder(final PaymentOrder paymentOrder, final String paymentId) throws RazorpayException {
        if (paymentOrder.getPaymentOrderStatus().equals((PaymentOrderStatus.PENDING))) {
            if (paymentOrder.getPaymentGateway().equals(PaymentGateway.RAZORPAY)) {
                RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey, razorpayApiSecret);
                Payment payment = razorpayClient.payments.fetch(paymentId);
                Integer amount = payment.get("amount");
                String status = payment.get("status");
                if (status.equals("captured")) {
                    paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
                    return true;
                } else {
                    paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.FAILED);
                    return false;
                }
            }
            paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderRepo.save(paymentOrder);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public PaymentResponse createRazorpayOrder(final User user, final Long amount) throws RazorpayException {
        Long amountInPaise = amount * 100;

        try {
            RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey, razorpayApiSecret);

            JSONObject paymentLinkRequest = getJsonObject(user, amountInPaise);

            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = paymentLink.get("id");
            String paymentLinkUrl = paymentLink.get("short_url");

            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setPaymentUrl(paymentLinkUrl);

            return paymentResponse;
        } catch (RazorpayException e) {
            log.error("Error creating payment link: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private static JSONObject getJsonObject(final User user, final Long amountInPaise) {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amountInPaise);
        paymentLinkRequest.put("currency", "INR");

        JSONObject customer = new JSONObject();
        customer.put("name", user.getUsername());
        customer.put("email", user.getEmail());

        paymentLinkRequest.put("customer", customer);

        JSONObject notify = new JSONObject();
        notify.put("email", true);
        paymentLinkRequest.put("notify", notify);

        paymentLinkRequest.put(("reminder_enable"), true);

        paymentLinkRequest.put("callback_url", "http://localhost:5173/wallet");
        paymentLinkRequest.put("callback_method", "get");
        return paymentLinkRequest;
    }

    @Override
    public PaymentResponse createStripeOrder(final User user, final Long amount, final Long orderId) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:5173/wallet?orderId=" + orderId)
                        .setCancelUrl("http://localhost:5173/payment/cancel")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("usd")
                                                        .setUnitAmount(amount * 100)
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("Add Money to Wallet")
                                                                        .build())
                                                        .build())
                                        .build())
                        .build();

        Session session = Session.create(params);

        System.out.println("session _____" + session);
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentUrl(session.getUrl());
        return paymentResponse;
    }
}
