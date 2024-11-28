package com.example.tradingapp.Repository;

import com.example.tradingapp.model.PaymentOrder;
import com.example.tradingapp.response.PaymentResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

}
