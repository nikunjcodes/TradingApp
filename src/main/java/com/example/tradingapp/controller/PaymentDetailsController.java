package com.example.tradingapp.controller;

import com.example.tradingapp.model.PaymentDetails;
import com.example.tradingapp.model.User;
import com.example.tradingapp.service.PaymentsDetailsService;
import com.example.tradingapp.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentDetailsController {
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentsDetailsService paymentsDetailsService;
    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetails> addPaymentDetails(
            @RequestBody PaymentDetails paymentDetailsRequest,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetails paymentDetails = paymentsDetailsService.addPaymentDetails(
                paymentDetailsRequest.getAccountNumber(),
                paymentDetailsRequest.getAccountHolderName(),
                paymentDetailsRequest.getIfsc(),
                paymentDetailsRequest.getBankName(),
                user
        );
        return new ResponseEntity<>(paymentDetails , HttpStatus.CREATED);


    }
    @GetMapping("/payment-details")
    public ResponseEntity<PaymentDetails> getUsersPaymentDetails(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetails paymentDetails = paymentsDetailsService.getUsersPaymentDetails(user);
return new ResponseEntity<>(paymentDetails,HttpStatus.CREATED);
    }

}

