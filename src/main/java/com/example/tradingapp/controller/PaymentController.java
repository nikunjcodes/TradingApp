package com.example.tradingapp.controller;

import com.example.tradingapp.domain.PaymentMethod;
import com.example.tradingapp.model.Order;
import com.example.tradingapp.model.PaymentOrder;
import com.example.tradingapp.model.User;
import com.example.tradingapp.model.Wallet;
import com.example.tradingapp.response.PaymentResponse;
import com.example.tradingapp.service.OrderService;
import com.example.tradingapp.service.PaymentService;
import com.example.tradingapp.service.UserService;
import com.example.tradingapp.service.WalletService;
import com.razorpay.Payment;
import com.razorpay.RazorpayException;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequestMapping
@RestController
public class PaymentController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentMethod(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentResponse paymentResponse = null;
        PaymentOrder order = paymentService.createOrder(user , amount , paymentMethod);
        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse = paymentService.createRazorpayPaymentLink(user , amount);
        }else if(paymentMethod.equals(PaymentMethod.STRIPE)){
            paymentResponse = paymentService.createStripePaymentLink(user , amount , order.getId());
        }
        return new ResponseEntity<>(paymentResponse , HttpStatus.CREATED);
    }
    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.getOrderById(orderId);
        Wallet wallet = walletService.payOrderPayment(order , user);
return new ResponseEntity<>(wallet , HttpStatus.ACCEPTED);

    }
    @PutMapping("/api/wallet/deposit")
    public ResponseEntity<Wallet> addBalanceToWallet(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(name = "order_id") Long orderId,
            @RequestParam(name = "payment_id") String paymentId
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet  = walletService.getUserWallet( user);
        PaymentOrder order = paymentService.getPaymentOrderById(orderId);
        Boolean status = paymentService.proceedPaymentOrder(order , paymentId);
        if(status){
            walletService.addBalance(wallet , order.getAmount());
        }
        return new ResponseEntity<>(wallet , HttpStatus.ACCEPTED);
    }

}
