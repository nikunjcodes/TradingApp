package com.example.tradingapp.service;

import com.example.tradingapp.model.User;
import com.example.tradingapp.model.Withdrawal;
import lombok.With;

import java.util.List;

public interface WithdrawalService {
    Withdrawal requestWithdrawal(Long amount, User user) throws Exception;

    Withdrawal proceedWithdrawal(Long withdrawalId , boolean accept) throws Exception;

    List<Withdrawal> getUsersWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequest();
}
