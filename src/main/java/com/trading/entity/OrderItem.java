package com.trading.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 01:37
 */
@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    private Double quantity;
    private Double buyPrice;
    private Double sellPrice;
    @ManyToOne
    private Coin coin;
    @OneToOne
    @JsonIgnore
    private Order order;
}
