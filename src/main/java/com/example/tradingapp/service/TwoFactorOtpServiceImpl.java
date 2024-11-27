package com.example.tradingapp.service;

import com.example.tradingapp.Repository.TwoFactorOtpRepository;
import com.example.tradingapp.model.TwoFactorOtp;
import com.example.tradingapp.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService{
    private TwoFactorOtpRepository twoFactorOtpRepository;
    @Override
    public TwoFactorOtp createTwoFactorOtp(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        TwoFactorOtp twoFactorOtp = new TwoFactorOtp();
        twoFactorOtp.setOtp(otp);
        twoFactorOtp.setId(id);
        twoFactorOtp.setUser(user);
        twoFactorOtp.setJwt(jwt);
        return twoFactorOtpRepository.save(twoFactorOtp);

    }

    @Override
    public TwoFactorOtp findByUser(Long userId) {
        return twoFactorOtpRepository.findByUserId(userId);
    }

    @Override
    public TwoFactorOtp findById(String id) {
        Optional<TwoFactorOtp> opt = twoFactorOtpRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOtp twoFactorOtp, String otp) {
        return twoFactorOtp.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOtp twoFactorOtp) {
        twoFactorOtpRepository.delete(twoFactorOtp);

    }
}
