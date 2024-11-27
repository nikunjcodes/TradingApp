package com.example.tradingapp.Repository;

import com.example.tradingapp.model.TwoFactorOtp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOtp , String> {
    TwoFactorOtp findByUserId(Long userId);
}