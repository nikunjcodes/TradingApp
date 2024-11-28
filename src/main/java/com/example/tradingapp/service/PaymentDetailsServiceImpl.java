package com.example.tradingapp.service;

import com.example.tradingapp.Repository.PaymentDetailsRepository;
import com.example.tradingapp.model.PaymentDetails;
import com.example.tradingapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsServiceImpl implements PaymentsDetailsService{
    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName, User user) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setUser(user);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setBankName(bankName);
        return paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {
        return paymentDetailsRepository.findByUserId(user.getId());
    }
}
