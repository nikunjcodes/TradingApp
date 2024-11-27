package com.example.tradingapp.controller;

import com.example.tradingapp.model.Order;
import com.example.tradingapp.model.User;
import com.example.tradingapp.model.Wallet;
import com.example.tradingapp.model.WalletTransaction;
import com.example.tradingapp.service.OrderService;
import com.example.tradingapp.service.UserService;
import com.example.tradingapp.service.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tradingapp.service.WalletService;
@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet  =walletService.getUserWallet(user);
        return new ResponseEntity<>(wallet , HttpStatus.ACCEPTED);
    }
    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt , @PathVariable Long walletId , @RequestBody WalletTransaction req) throws Exception{
        User senderUser = userService.findUserProfileByJwt(jwt);
        Wallet receiverWallet = walletService.findById(walletId);
        Wallet wallet = walletService.walletToUserTransfer(senderUser , receiverWallet , req.getAmount());
        return new ResponseEntity<>(wallet , HttpStatus.ACCEPTED);
    }
    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt , @PathVariable Long orderId) throws Exception{
       User user = userService.findUserProfileByJwt(jwt);
       Order order = orderService.getOrderById(orderId);
       Wallet wallet = walletService.payOrderPayment(order , user);
       return new ResponseEntity<>(wallet , HttpStatus.ACCEPTED);

    }

}
