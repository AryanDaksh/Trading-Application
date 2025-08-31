package com.trading.service.impl;

import com.trading.entity.Order;
import com.trading.entity.User;
import com.trading.entity.Wallet;
import com.trading.enums.OrderType;
import com.trading.repository.WalletRepo;
import com.trading.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 01:12
 */
@RequiredArgsConstructor
@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepo walletRepo;

    @Override
    public Wallet getUserWallet(final User user) {
        Wallet wallet = walletRepo.findByUserId(user.getId());
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUser(user);
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(final Wallet wallet, final Long amount) {
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(amount));
        wallet.setBalance(newBalance);
        return walletRepo.save(wallet);
    }

    @Override
    public Wallet findWalletById(final Long id) throws Exception {
        Optional<Wallet> wallet = walletRepo.findById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        }
        throw new Exception("Wallet not found!");
    }

    @Override
    public Wallet walletToWalletTransfer(final User sender, final Wallet receiverWallet, final Long amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);
        if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new Exception("Insufficient balance!");
        }
        BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(senderBalance);
        walletRepo.save(senderWallet);
        BigDecimal receiverBalance = receiverWallet.getBalance().add(BigDecimal.valueOf(amount));
        receiverWallet.setBalance(receiverBalance);
        walletRepo.save(receiverWallet);
        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(final Order order, final User user) throws Exception {
        Wallet wallet = getUserWallet(user);
        if (order.getOrderType() == OrderType.BUY) {
            BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
            if (newBalance.compareTo(order.getPrice()) < 0) {
                throw new Exception("Insufficient balance!");
            }
            wallet.setBalance(newBalance);
        }
        else if (order.getOrderType() == OrderType.SELL) {
            BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }
        walletRepo.save(wallet);
        return wallet;
    }
}
