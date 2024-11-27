package com.example.tradingapp.service;

import com.example.tradingapp.Repository.OrderItemRepository;
import com.example.tradingapp.Repository.OrderRepository;
import com.example.tradingapp.domain.OrderStatus;
import com.example.tradingapp.domain.OrderType;
import com.example.tradingapp.model.Coin;
import com.example.tradingapp.model.Order;
import com.example.tradingapp.model.OrderItem;
import com.example.tradingapp.model.User;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Service

public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WalletService walletService;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) throws Exception {
        double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();
        Order order = new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimeStamp(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        return orderRepository.save(order);

    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId).orElseThrow(()-> new Exception("Order not found"));
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, String orderType, String assertSymbol) {
        return orderRepository.findByUserId(userId);
    }
    private OrderItem createOrderItem(Coin coin , double quantity , double buyPrice , double sellPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepository.save(orderItem);
    }
    @Transactional
    public Order buyAsset(Coin coin , double quantity , User user) throws Exception {
        if(quantity <= 0) {
            throw new Exception("Quantity should be greater than 0");
        }

        OrderItem orderItem = createOrderItem(coin , quantity , coin.getCurrentPrice() , coin.getCurrentPrice());

        Order order =  createOrder(user , orderItem , OrderType.BUY);
        orderItem.setOrder(order);
        walletService.payOrderPayment(order , user);
        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder = orderRepository.save(order);

        // create asset
        return savedOrder;

    }
    @Transactional
    public Order sellAssest(Coin coin , double quantity , User user) throws Exception {
        if(quantity <= 0) {
            throw new Exception("Quantity should be greater than 0");
        }
        double sellPrice = coin.getCurrentPrice();
        double buyPrice = assestToSell.getPrice();

        OrderItem orderItem = createOrderItem(coin , quantity , buyPrice , sellPrice);

        Order order =  createOrder(user , orderItem , OrderType.SELL);
        orderItem.setOrder(order);
        if(assestToSell.getQuantity() >=quantity) {
             walletService.payOrderPayment(order , user);
             Asset updatedAsset = assestService.updateAssest(assestToSell.getId() , -quantity);
             if(updatedAsset.getQuantity() + coin.getCurrentPrice()<=1){
                 assestService.deleteAssest(assestToSell.getId());
             }
            walletService.payOrderPayment(order , user);
            order.setOrderStatus(OrderStatus.SUCCESS);
            order.setOrderType(OrderType.SELL);
            return orderRepository.save(order);
        }


        // create asset
        throw new Exception("Insufficient quantity to sell");


    }
    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
        if(orderType.equals(OrderType.BUY))
            return buyAsset(coin , quantity , user);
        else if(orderType.equals(OrderType.SELL))
            return sellAssest(coin , quantity , user);
        else
            throw new Exception("Invalid Order Type");
    }

}
