package com.trading.web.controller;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.trading.entity.PaymentOrder;
import com.trading.entity.User;
import com.trading.enums.PaymentGateway;
import com.trading.service.PaymentOrderService;
import com.trading.service.UserService;
import com.trading.web.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 29-09-2025 23:02
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentOrderService paymentOrderService;
    private final UserService userService;

    @PostMapping("/payments/{paymentGateway}/amount/{amount}")
    public ResponseEntity<PaymentResponse> createPaymentOrder(final @PathVariable PaymentGateway paymentGateway, final @PathVariable Long amount, final @RequestHeader("Authorization") String jwt) throws RazorpayException, StripeException {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentResponse paymentResponse = new PaymentResponse();
        PaymentOrder paymentOrder = paymentOrderService.createPaymentOrder(user, amount, paymentGateway);
        if (paymentGateway.equals(PaymentGateway.RAZORPAY)) {
            paymentResponse = paymentOrderService.createRazorpayOrder(user, amount);
        } else if (paymentGateway.equals(PaymentGateway.STRIPE)) {
            paymentResponse = paymentOrderService.createStripeOrder(user, amount, paymentOrder.getId());
        }
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }
}
