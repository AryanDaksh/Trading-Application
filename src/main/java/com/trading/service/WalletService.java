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
    Wallet getUserWallet(final User user);
    Wallet addBalance(final Wallet wallet, final Long amount);
    Wallet findWalletById(final Long id) throws Exception;
    Wallet walletToWalletTransfer(final User sender, final Wallet receiverWallet, final Long amount) throws Exception;
    Wallet payOrderPayment(final Order order, final User user) throws Exception;
}
