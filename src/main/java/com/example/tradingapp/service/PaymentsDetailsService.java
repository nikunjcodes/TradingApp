package com.example.tradingapp.service;

import com.example.tradingapp.model.PaymentDetails;
import com.example.tradingapp.model.User;

public interface PaymentsDetailsService {
    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifsc,
                                            String bankName,
                                            User user);
    public PaymentDetails getUsersPaymentDetails(User user);
}
