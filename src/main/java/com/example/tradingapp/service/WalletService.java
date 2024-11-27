package com.example.tradingapp.service;

import com.example.tradingapp.model.Order;
import com.example.tradingapp.model.User;
import com.example.tradingapp.model.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet , Long money);
    Wallet findById(Long id) throws Exception;
    Wallet walletToUserTransfer(User sender , Wallet receiverWallet , Long amount) throws Exception;
    Wallet payOrderPayment(Order order , User user);
}
