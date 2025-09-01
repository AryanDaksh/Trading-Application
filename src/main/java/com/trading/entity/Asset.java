package com.trading.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 23:37
 */
@Data
@Entity
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double quantity;
    private Double buyingPrice;
    @ManyToOne
    private Coin coin;
    @ManyToOne
    private User user;
}
