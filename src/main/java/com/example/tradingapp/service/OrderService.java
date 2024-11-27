package com.example.tradingapp.service;

import com.example.tradingapp.domain.OrderType;
import com.example.tradingapp.model.Coin;
import com.example.tradingapp.model.Order;
import com.example.tradingapp.model.OrderItem;
import com.example.tradingapp.model.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user , OrderItem orderItem , OrderType orderType) throws Exception;

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId , String orderType , String assertSymbol);

    Order processOrder(Coin coin , double quantity , OrderType orderType , User user) throws Exception;
}
