package com.trading.service;

import com.trading.entity.Order;
import com.trading.entity.User;
import com.trading.entity.Wallet;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 01:12
 */
public interface WalletService {
    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long amount);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;
}
