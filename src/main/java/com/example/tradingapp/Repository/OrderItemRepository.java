package com.example.tradingapp.Repository;

import com.example.tradingapp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem , Long> {

}
