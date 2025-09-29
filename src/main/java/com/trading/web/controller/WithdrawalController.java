package com.trading.web.controller;

import com.trading.entity.User;
import com.trading.entity.Wallet;
import com.trading.entity.WalletTransaction;
import com.trading.entity.Withdrawal;
import com.trading.service.UserService;
import com.trading.service.WalletService;
import com.trading.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 02-09-2025 00:29
 */
@RequiredArgsConstructor
@RequestMapping("/api/withdrawal")
@RestController
public class WithdrawalController {
    private final WithdrawalService withdrawalService;
    private final UserService userService;
    private WalletService walletService;

    @PostMapping("/{amount}")
    public ResponseEntity<?> withdrawalRequest(final @PathVariable Long amount, final @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);
        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
        walletService.addBalance(wallet, -withdrawal.getAmount());
        //WalletTransaction walletTransaction = wa
        return new  ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @PatchMapping("/admin/{id}/proceed/{accept}")
    public ResponseEntity<?> processWithdrawal(final @PathVariable Long id, final @PathVariable Boolean accept, final @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Withdrawal withdrawal = withdrawalService.processWithdrawal(id, accept);
        Wallet wallet = walletService.getUserWallet(user);
        if(!accept) {
            walletService.addBalance(wallet, withdrawal.getAmount());
        }
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping("/admin/history")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalHistory(final @RequestHeader("Authorization") String jwt) {
        List<Withdrawal> withdrawals = withdrawalService.getAllWithdrawals();
        return new ResponseEntity<>(withdrawals, HttpStatus.OK);
    }
}
