package com.example.tradingapp.service;

import com.example.tradingapp.model.TwoFactorOtp;
import com.example.tradingapp.model.User;
public interface TwoFactorOtpService {
    TwoFactorOtp createTwoFactorOtp(User user , String otp , String jwt);
    TwoFactorOtp findByUser(Long userId);
    TwoFactorOtp findById(String id);

    boolean verifyTwoFactorOtp(TwoFactorOtp twoFactorOtp , String otp);

    void deleteTwoFactorOtp(TwoFactorOtp twoFactorOtp);
}
