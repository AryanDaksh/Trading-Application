package com.trading.entity;

import com.trading.enums.OrderStatus;
import com.trading.enums.OrderType;
import com.trading.utils.DateUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 01:19
 */
@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    @Column(nullable = false)
    private OrderType orderType;
    @Column(nullable = false)
    private OrderStatus orderStatus;
    @Column(nullable = false)
    private BigDecimal price;
    private LocalDateTime timestamp = DateUtils.getCurrentDateTime();
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderItem orderItem;
}
