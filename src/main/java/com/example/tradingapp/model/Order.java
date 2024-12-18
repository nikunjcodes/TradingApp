package com.example.tradingapp.model;

import com.example.tradingapp.domain.OrderStatus;
import com.example.tradingapp.domain.OrderType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    @Column(nullable = false)
    private OrderType orderType;
    @Column(nullable = false)
    private BigDecimal price;
    private LocalDateTime timeStamp = LocalDateTime.now();
    @Column(nullable = false)
    private OrderStatus orderStatus;
    @OneToOne(mappedBy = "order" , cascade = CascadeType.ALL)
    private OrderItem orderItem;



}
