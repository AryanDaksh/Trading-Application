package com.trading.web.controller;

import com.trading.entity.PaymentDetails;
import com.trading.entity.User;
import com.trading.service.PaymentDetailsService;
import com.trading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 05-09-2025 22:37
 */
@RequiredArgsConstructor
@RestController("/api")
public class PaymentDetailsController {
    private final PaymentDetailsService paymentDetailsService;
    private final UserService userService;

    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetails> addPaymentDetails(final @RequestBody PaymentDetails paymentDetailsRequest, final @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(paymentDetailsRequest.getAccountNumber(),  paymentDetailsRequest.getAccountHolderName(), paymentDetailsRequest.getIfscCode(), paymentDetailsRequest.getBankName(), user);
        return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);
    }

    @GetMapping("/payment-details")
    public ResponseEntity<PaymentDetails> getPaymentDetailsByUser(final @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetails paymentDetails = paymentDetailsService.getPaymentDetailsByUser(user);
        return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
    }
}
