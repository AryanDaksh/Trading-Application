package com.trading.web.controller;

import com.trading.entity.Coin;
import com.trading.entity.Order;
import com.trading.entity.User;
import com.trading.entity.WalletTransaction;
import com.trading.enums.OrderType;
import com.trading.service.CoinService;
import com.trading.service.OrderService;
import com.trading.service.UserService;
import com.trading.service.WalletTransactionService;
import com.trading.web.request.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 19:18
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final CoinService coinService;
    private final WalletTransactionService walletTransactionService;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(final @RequestHeader("Authorization") String jwt, final @RequestBody CreateOrderRequest createOrderRequest) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findCoinById(createOrderRequest.getCoinId());
        Order order = orderService.processOrder(coin, createOrderRequest.getQuantity(), createOrderRequest.getOrderType(), user);
        if (order.getUser().getId().equals(user.getId())) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            throw new Exception("Access denied!");
        }
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrdersForUser(@RequestHeader("Authorization") String jwt, @RequestParam(required = false) OrderType orderType, @RequestParam(required = false) String assetSymbol) throws Exception {
        Long userId = userService.findUserProfileByJwt(jwt).getId();
        List<Order> userOrders = orderService.getAllOrders(userId, orderType, assetSymbol);
        return new ResponseEntity<>(userOrders, HttpStatus.OK);
    }
}
