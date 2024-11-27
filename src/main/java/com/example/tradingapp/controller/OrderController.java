package com.example.tradingapp.controller;

import com.example.tradingapp.model.Coin;
import com.example.tradingapp.model.Order;
import com.example.tradingapp.request.CreateOrderRequest;
import com.example.tradingapp.service.CoinService;
import com.example.tradingapp.service.OrderService;
import com.example.tradingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tradingapp.model.User;

import java.util.List;

@RestController
@RequestMapping("/api/order")

public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CoinService coinService;
//    @Autowired
//    private WalletTransactionService walletTransactionService;
    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest req
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin =  coinService.findById(req.getCoinId());

        Order order = orderService.processOrder(coin,req.getQuantity(),req.getOrderType() , user);
        return ResponseEntity.ok(order);

    }
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.getOrderById(orderId);
        if(order.getUser().getId() != user.getId()){
            throw new Exception("Order not found");
        }
        return ResponseEntity.ok(order);
    }
    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrdersOfUser(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(required = false) String orderType,
            @RequestParam(required = false) String assertSymbol
    ) throws Exception {
        Long userId = userService.findUserProfileByJwt(jwt).getId();
        List<Order> userOrders = orderService.getAllOrdersOfUser(userId , orderType , assertSymbol);

        return ResponseEntity.ok(userOrders);
    }
}

