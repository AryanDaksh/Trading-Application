package com.trading.entity;

import com.trading.enums.WalletTransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 14:32
 */
@Data
@Entity
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Wallet wallet;
    private WalletTransactionType walletTransactionType;
    private LocalDate localDate;
    private String transferId;
    private String purpose;
    private Long amount;
}
