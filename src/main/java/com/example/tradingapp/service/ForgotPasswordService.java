package com.example.tradingapp.service;

import com.example.tradingapp.domain.VerificationType;
import com.example.tradingapp.model.ForgotPasswordToken;
import com.example.tradingapp.model.User;

public interface ForgotPasswordService {
    ForgotPasswordToken createToken(User user ,
                                    String id ,
                                    String otp ,
                                    VerificationType  verificationType ,
                                    String sendTo);
    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
