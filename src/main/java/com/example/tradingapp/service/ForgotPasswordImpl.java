package com.example.tradingapp.service;

import com.example.tradingapp.Repository.ForgotPasswordRepository;
import com.example.tradingapp.domain.VerificationType;
import com.example.tradingapp.model.ForgotPasswordToken;
import com.example.tradingapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class ForgotPasswordImpl implements ForgotPasswordService{
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Override
    public ForgotPasswordToken createToken(User user,
                                           String id,
                                           String otp,
                                           VerificationType verificationType,
                                           String sendTo) {
        ForgotPasswordToken token = new ForgotPasswordToken();
        token.setUser(user);
        token.setSendTo(sendTo);
        token.setOtp(otp);
        token.setId(id);
        token.setVerificationType(verificationType);
        return forgotPasswordRepository.save(token);
    }

    @Override
    public ForgotPasswordToken findById(String id) {

        Optional<ForgotPasswordToken> token = forgotPasswordRepository.findById(id);
        return  token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long userId) {
        return forgotPasswordRepository.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
        forgotPasswordRepository.delete(token);
    }
}
