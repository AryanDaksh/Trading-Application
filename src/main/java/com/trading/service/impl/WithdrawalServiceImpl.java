package com.trading.service.impl;

import com.trading.entity.User;
import com.trading.entity.Withdrawal;
import com.trading.enums.WithdrawalStatus;
import com.trading.repository.WithdrawalRepo;
import com.trading.service.WithdrawalService;
import com.trading.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 02-09-2025 00:20
 */
@RequiredArgsConstructor
@Service
public class WithdrawalServiceImpl implements WithdrawalService {
    private final WithdrawalRepo withdrawalRepo;

    @Override
    public Withdrawal requestWithdrawal(final Long amount, final User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setWithdrawalStatus(WithdrawalStatus.PENDING);
        return withdrawalRepo.save(withdrawal);
    }

    @Override
    public Withdrawal processWithdrawal(final Long withdrawalId, final Boolean acceptance) throws Exception {
        Optional<Withdrawal> optionalWithdrawal = withdrawalRepo.findById(withdrawalId);
        if (optionalWithdrawal.isEmpty()) {
            throw new Exception("Withdrawal not found");
        }
        Withdrawal withdrawal = optionalWithdrawal.get();
        withdrawal.setLocalDateTime(DateUtils.getCurrentDateTime());
        if(acceptance) {
            withdrawal.setWithdrawalStatus(WithdrawalStatus.SUCCESS);
        } else {
            withdrawal.setWithdrawalStatus(WithdrawalStatus.DENIED);
        }
        return withdrawalRepo.save(withdrawal);
    }

    @Override
    public List<Withdrawal> getUserWithdrawalHistory(final User user) {
        return withdrawalRepo.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawals() {
        return withdrawalRepo.findAll();
    }
}
