package com.trading.service;

import com.trading.entity.Coin;
import com.trading.entity.Order;
import com.trading.entity.OrderItem;
import com.trading.entity.User;
import com.trading.enums.OrderType;

import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 14:45
 */
public interface OrderService {
    Order createOrder(final User user, final OrderItem orderItem, final OrderType orderType);
    Order findOrderById(final Long id) throws Exception;
    List<Order> getAllOrders(final Long userId, final OrderType orderType, final String assetSymbol);
    Order processOrder(final Coin coin, final Double quantity, final OrderType orderType, final User user) throws Exception;
}
