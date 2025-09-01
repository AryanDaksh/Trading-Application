package com.trading.web.controller;

import com.trading.entity.Order;
import com.trading.entity.User;
import com.trading.entity.Wallet;
import com.trading.entity.WalletTransaction;
import com.trading.service.OrderService;
import com.trading.service.UserService;
import com.trading.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 14:25
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/get-wallet")
    public ResponseEntity<Wallet> getUserWallet(final @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);
        return new  ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/transfer/{walletId}")
    public ResponseEntity<Wallet> walletToWalletTransfer(final @RequestHeader("Authorization") String jwt, final @PathVariable("walletId") Long walletId, final @RequestBody WalletTransaction walletTransaction) throws Exception {
        User senderUser = userService.findUserProfileByJwt(jwt);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet wallet = walletService.walletToWalletTransfer(senderUser, receiverWallet, walletTransaction.getAmount());
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity<Wallet> payOrderPayment(final @RequestHeader("Authorization") String jwt, final @PathVariable("orderId") Long orderId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);
        Wallet wallet = walletService.payOrderPayment(order, user);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
}
