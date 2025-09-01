package com.trading.web.request;

import com.trading.enums.OrderType;
import lombok.Data;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 19:22
 */
@Data
public class CreateOrderRequest {
    private String coinId;
    private Double quantity;
    private OrderType orderType;
}
