package com.example.tradingapp.service;

import com.example.tradingapp.Repository.PaymentOrderRepository;
import com.example.tradingapp.domain.PaymentMethod;
import com.example.tradingapp.domain.PaymentOrderStatus;
import com.example.tradingapp.model.PaymentOrder;
import com.example.tradingapp.model.User;
import com.example.tradingapp.response.PaymentResponse;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecretKey;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.api.secret}")
    private String stripeApiSecretKey;

    @Override
    public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) {
        logger.info("Creating payment order for user: {}", user.getEmail());
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setMethod(paymentMethod);
        paymentOrder.setStatus(PaymentOrderStatus.PENDING); // Set default status as PENDING
        paymentOrder = paymentOrderRepository.save(paymentOrder);
        logger.info("Payment order created successfully with ID: {}", paymentOrder.getId());
        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        logger.info("Fetching payment order with ID: {}", id);
        return paymentOrderRepository.findById(id)
                .orElseThrow(() -> new Exception("Payment Order not found with ID: " + id));
    }

    @Override
    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {
            if (paymentOrder.getMethod().equals(PaymentMethod.RAZORPAY)) {
                try {
                    RazorpayClient razorpayClient = new RazorpayClient("rzp_test_VZvbrluBytjbfk", "pD2udISrkq4gIQp9CwSJW8eh");
                    Payment payment = razorpayClient.payments.fetch(paymentId);
                    String status = payment.get("status");

                    logger.info("Razorpay payment status for payment ID {}: {}", paymentId, status);

                    if ("captured".equals(status)) {
                        paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                        paymentOrderRepository.save(paymentOrder);
                        logger.info("Payment order with ID {} marked as SUCCESS", paymentOrder.getId());
                        return true;
                    } else {
                        paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                        paymentOrderRepository.save(paymentOrder);
                        logger.warn("Payment order with ID {} marked as FAILED", paymentOrder.getId());
                        return false;
                    }
                } catch (RazorpayException e) {
                    logger.error("Error fetching Razorpay payment for payment ID {}: {}", paymentId, e.getMessage(), e);
                    throw e; // Rethrow the exception to be handled elsewhere
                }
            } else if (paymentOrder.getMethod().equals(PaymentMethod.STRIPE)) {
                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepository.save(paymentOrder);
                logger.info("Payment order with ID {} marked as SUCCESS (Stripe)", paymentOrder.getId());
                return true;
            }
        }
        logger.warn("Payment order with ID {} is not in PENDING status", paymentOrder.getId());
        return false;
    }

    @Override
    public PaymentResponse createRazorpayPaymentLink(User user, Long amount) {
        Long amountInPaisa = amount * 100; // Razorpay uses paisa as the unit
        try {
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecretKey);
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amountInPaisa);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("description", "Top up Wallet");

            JSONObject customer = new JSONObject();
            customer.put("name", user.getFullName());
            customer.put("email", user.getEmail());
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("reminder_enable", true);
            paymentLinkRequest.put("callback_url", "http://localhost:5173/wallet");
            paymentLinkRequest.put("callback_method", "get");

            logger.debug("Payment Link Request: {}", paymentLinkRequest);

            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkUrl = paymentLink.get("short_url");
            PaymentResponse response = new PaymentResponse();
            response.setPayment_url(paymentLinkUrl);

            logger.info("Razorpay Payment Link created successfully: {}", paymentLinkUrl);

            return response;
        } catch (RazorpayException e) {
            logger.error("Error creating Razorpay payment link: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating Razorpay payment link", e);
        }
    }

    @Override
    public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws Exception {
        Stripe.apiKey = stripeApiSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/wallet?order_id=" + orderId)
                .setCancelUrl("http://localhost:5173/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount * 100) // Convert amount to cents
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Top up Wallet")
                                        .build())
                                .build())
                        .build())
                .build();

        try {
            Session session = Session.create(params);
            PaymentResponse response = new PaymentResponse();
            response.setPayment_url(session.getUrl());

            logger.info("Stripe Payment Link created successfully: {}", session.getUrl());

            return response;
        } catch (StripeException e) {
            logger.error("Error creating Stripe payment session: {}", e.getMessage(), e);
            throw new Exception("Error creating Stripe payment session", e);
        }
    }
}
