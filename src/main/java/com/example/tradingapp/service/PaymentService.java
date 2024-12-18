package com.example.tradingapp.service;

import com.example.tradingapp.domain.PaymentMethod;
import com.example.tradingapp.model.PaymentOrder;
import com.example.tradingapp.model.User;
import com.example.tradingapp.response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentOrder createOrder(User user , Long amount , PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean proceedPaymentOrder(PaymentOrder paymentOrder , String paymentId) throws RazorpayException;

    PaymentResponse createRazorpayPaymentLink(User user , Long amount);
    PaymentResponse createStripePaymentLink(User user , Long amount , Long orderId) throws Exception;

}
