package com.example.tradingapp.service;

import com.example.tradingapp.Repository.OrderItemRepository;
import com.example.tradingapp.Repository.OrderRepository;
import com.example.tradingapp.domain.OrderStatus;
import com.example.tradingapp.domain.OrderType;
import com.example.tradingapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AssetService assetService;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();

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
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, String orderType, String assetSymbol) {
        // Currently not using the orderType or assetSymbol parameters, but this can be extended
        return orderRepository.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);

        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public Order buyAsset(Coin coin, double quantity, User user) throws Exception {
        validateQuantity(quantity);

        OrderItem orderItem = createOrderItem(coin, quantity, coin.getCurrentPrice(), coin.getCurrentPrice());
        Order order = createOrder(user, orderItem, OrderType.BUY);

        // Link the order and the order item
        orderItem.setOrder(order);

        // Process payment for the order
        walletService.payOrderPayment(order, user);

        // Mark the order as successful
        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);

        // Save order to the database
        Order savedOrder = orderRepository.save(order);

        // Manage assets
        handleAssetsAfterPurchase(user, orderItem);

        return savedOrder;
    }

    @Transactional
    public Order sellAsset(Coin coin, double quantity, User user) throws Exception {
        validateQuantity(quantity);

        Asset assetToSell = assetService.findAssesByUserIdAndCoinId(user.getId(), coin.getId());
        if (assetToSell == null || assetToSell.getQuantity() < quantity) {
            throw new Exception("Insufficient asset quantity to sell");
        }

        double sellPrice = coin.getCurrentPrice();
        double buyPrice = assetToSell.getBuyPrice();

        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
        Order order = createOrder(user, orderItem, OrderType.SELL);

        // Link the order and the order item
        orderItem.setOrder(order);

        // Process payment for the order
        walletService.payOrderPayment(order, user);

        // Update the asset after the sale
        Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);

        if (updatedAsset.getQuantity() == 0) {
            assetService.deleteAsset(assetToSell.getId());
        }

        // Mark the order as successful
        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.SELL);

        return orderRepository.save(order);
    }

    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
        if (orderType == OrderType.BUY) {
            return buyAsset(coin, quantity, user);
        } else if (orderType == OrderType.SELL) {
            return sellAsset(coin, quantity, user);
        } else {
            throw new Exception("Invalid Order Type");
        }
    }

    private void validateQuantity(double quantity) throws Exception {
        if (quantity <= 0) {
            throw new Exception("Quantity should be greater than 0");
        }
    }

    private void handleAssetsAfterPurchase(User user, OrderItem orderItem) {
        Asset existingAsset = assetService.findAssesByUserIdAndCoinId(user.getId(), orderItem.getCoin().getId());
        if (existingAsset == null) {
            assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
        } else {
            assetService.updateAsset(existingAsset.getId(), orderItem.getQuantity());
        }
    }
}
