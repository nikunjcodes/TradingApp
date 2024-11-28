package com.example.tradingapp.controller;

import com.example.tradingapp.domain.WalletTransactionType;
import com.example.tradingapp.model.User;
import com.example.tradingapp.model.Wallet;
import com.example.tradingapp.model.WalletTransaction;
import com.example.tradingapp.model.Withdrawal;
import com.example.tradingapp.service.UserService;
import com.example.tradingapp.service.WalletService;
import com.example.tradingapp.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WithdrawalController {
    @Autowired
    private WithdrawalService withdrawalService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;

    //    @Autowired
//    private WalletTransactionService walletTransactionService;
    @PostMapping("/api/withdrawal/{amount}")
    public ResponseEntity<?> withdrawalRequest(@PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
        Wallet userWallet = walletService.getUserWallet(user);
        walletService.addBalance(userWallet, -withdrawal.getAmount());
//        WalletTransaction walletTransaction = walletTransactionService.createTransaction(userWallet , WalletTransactionType.WITHDRAWAL  , "Withdrawal" ,  withdrawal.getAmount());
//
        return ResponseEntity.ok(withdrawal);
    }

    @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(@PathVariable Long id, @PathVariable boolean accept, @RequestHeader("Authorization") String jwt) throws Exception {
        Withdrawal withdrawal = withdrawalService.proceedWithdrawal(id, accept);
        Wallet userWallet = walletService.getUserWallet(withdrawal.getUser());
        if (!accept)
            walletService.addBalance(userWallet, withdrawal.getAmount());
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }
    @GetMapping("/api/withdrawal")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Withdrawal> withdrawalList = withdrawalService.getUsersWithdrawalHistory(user);
        return new ResponseEntity<>(withdrawalList, HttpStatus.ACCEPTED);
    }
    @GetMapping("/api/admin/withdrawal")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(
            @RequestHeader("Authorization") String jwt) throws Exception {
        List<Withdrawal> withdrawalList = withdrawalService.getAllWithdrawalRequest();
        return new ResponseEntity<>(withdrawalList, HttpStatus.ACCEPTED);
    }
}
