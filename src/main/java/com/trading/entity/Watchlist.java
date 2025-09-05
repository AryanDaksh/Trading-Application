package com.trading.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 03-09-2025 23:27
 */
@Entity
@Data
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User user;
    @ManyToMany
    private List<Coin> coins = new ArrayList<>();
}
