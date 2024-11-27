package com.example.tradingapp.model;

import com.example.tradingapp.domain.WalletTransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDate;

@Data
@Entity
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Wallet wallet;

    private WalletTransactionType walletTransactionType;
    private LocalDate date;
    private String transferId;
    private String purpose;
    private Long amount;


}
