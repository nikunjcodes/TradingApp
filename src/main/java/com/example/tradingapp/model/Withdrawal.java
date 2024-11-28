package com.example.tradingapp.model;

import com.example.tradingapp.domain.WithdrawalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private WithdrawalStatus withdrawalStatus;

    private long amount;
    @ManyToOne
    private User user;

    private LocalDateTime date = LocalDateTime.now();
}
