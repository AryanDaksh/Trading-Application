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
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order findOrderById(Long id) throws Exception;
    List<Order> getAllOrders(Long userId, OrderType orderType, String assetSymbol);
    Order processOrder(Coin coin, Double quantity, OrderType orderType, User user) throws Exception;
}
