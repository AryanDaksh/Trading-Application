package com.trading.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 01:13
 */
@Data
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User user;
    private BigDecimal balance;
}
