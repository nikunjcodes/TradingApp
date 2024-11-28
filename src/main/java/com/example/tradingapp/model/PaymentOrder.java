package com.example.tradingapp.model;

import com.example.tradingapp.domain.PaymentMethod;
import com.example.tradingapp.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentOrderStatus status;

    private PaymentMethod method;

    @ManyToOne
    private User user;
}
