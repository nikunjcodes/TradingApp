package com.example.tradingapp.Repository;

import com.example.tradingapp.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails , Long> {
    PaymentDetails findByUserId(Long userId);
}
