package com.trading.entity;

import com.trading.enums.PaymentGateway;
import com.trading.enums.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 05-09-2025 22:45
 */
@Data
@Entity
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long amount;
    private PaymentOrderStatus paymentOrderStatus;
    private PaymentGateway paymentGateway;
    @ManyToOne
    private User user;
}
