package com.trading.entity;

import com.trading.enums.WithdrawalStatus;
import com.trading.utils.DateUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 02-09-2025 00:17
 */
@Data
@Entity
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private WithdrawalStatus withdrawalStatus;
    private Long amount;
    @ManyToOne
    private User user;
    private LocalDateTime localDateTime = DateUtils.getCurrentDateTime();
}
