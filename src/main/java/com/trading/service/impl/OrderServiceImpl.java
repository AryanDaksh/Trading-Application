package com.trading.service.impl;

import com.trading.entity.*;
import com.trading.enums.OrderStatus;
import com.trading.enums.OrderType;
import com.trading.repository.OrderItemRepo;
import com.trading.repository.OrderRepo;
import com.trading.service.AssetService;
import com.trading.service.OrderService;
import com.trading.service.WalletService;
import com.trading.utils.DateUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.trading.enums.OrderStatus.PENDING;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 18:45
 */
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final WalletService walletService;
    private final OrderItemRepo orderItemRepo;
    private final AssetService assetService;

    @Override
    public Order createOrder(final User user, final OrderItem orderItem, final OrderType orderType) {
        double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();
        Order order = new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimestamp(DateUtils.getCurrentDateTime());
        order.setOrderStatus(PENDING);
        return orderRepo.save(order);
    }

    @Override
    public Order findOrderById(final Long id) throws Exception {
        return orderRepo.findById(id).orElseThrow(() -> new Exception("Order not found!"));
    }

    @Override
    public List<Order> getAllOrders(final Long userId, final OrderType orderType, final String assetSymbol) {
        return orderRepo.findByUserId(userId);
    }

    @Override
    @Transactional
    public Order processOrder(final Coin coin, final Double quantity, final OrderType orderType, final User user) throws Exception {
        if (orderType.equals(OrderType.BUY)) {
            return buyAsset(coin, quantity, user);
        } else if (orderType.equals(OrderType.SELL)) {
            return sellAsset(coin, quantity, user);
        }
        throw  new Exception("Invalid order type!");
    }

    @Transactional
    public Order buyAsset(final Coin coin, final Double quantity, final User user) throws Exception {
        if (quantity <= 0) {
            throw new Exception("Quantity must be greater than zero!");
        }
        Double buyPrice = coin.getCurrentPrice();
        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0.0);
        Order order = createOrder(user, orderItem, OrderType.BUY);
        orderItem.setOrder(order);
        walletService.payOrderPayment(order, user);
        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder = orderRepo.save(order);
        Asset oldAsset = assetService.findAssetByUserIdAndCoinId(order.getUser().getId(), order.getOrderItem().getCoin().getId());
        if (oldAsset == null) {
            assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
        } else {
            assetService.updateAsset(oldAsset.getId(), quantity);
        }
        return savedOrder;
    }

    @Transactional
    public Order sellAsset(final Coin coin, final Double quantity, final User user) throws Exception {
        if (quantity <= 0) {
            throw new Exception("Quantity must be greater than zero!");
        }
        Double sellPrice = coin.getCurrentPrice();
        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());
        if (assetToSell != null) {
            Double buyPrice = assetToSell.getBuyingPrice();
            OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
            Order order = createOrder(user, orderItem, OrderType.SELL);
            orderItem.setOrder(order);
            if (assetToSell.getQuantity() >= quantity) {
                walletService.payOrderPayment(order, user);
                order.setOrderStatus(OrderStatus.SUCCESS);
                order.setOrderType(OrderType.SELL);
                Order savedOrder = orderRepo.save(order);
                Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);
                if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
                    assetService.deleteAsset(updatedAsset.getId());
                }
                return savedOrder;
            } else {
                throw new Exception("Insufficient asset quantity to sell!");
            }
        }
        throw new Exception("No asset found to sell!");
    }

    private OrderItem createOrderItem(final Coin coin, final Double quantity, final Double buyPrice, final Double sellPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepo.save(orderItem);
    }
}
