package com.example.tradingapp.Repository;

import com.example.tradingapp.model.ForgotPasswordToken;
import com.example.tradingapp.service.ForgotPasswordImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken , String> {
    ForgotPasswordToken findByUserId(Long userId);
}
