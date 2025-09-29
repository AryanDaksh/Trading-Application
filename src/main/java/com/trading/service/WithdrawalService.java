package com.trading.service;

import com.trading.entity.User;
import com.trading.entity.Withdrawal;

import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 02-09-2025 00:20
 */
public interface WithdrawalService {
    Withdrawal requestWithdrawal(final Long amount, final User user);
    Withdrawal processWithdrawal(final Long withdrawalId, final Boolean acceptance) throws Exception;
    List<Withdrawal> getUserWithdrawalHistory(final User user);
    List<Withdrawal> getAllWithdrawals();
}
