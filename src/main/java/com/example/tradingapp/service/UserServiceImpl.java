package com.example.tradingapp.service;

import com.example.tradingapp.Repository.UserRepository;
import com.example.tradingapp.config.JwtProvider;
import com.example.tradingapp.domain.VerificationType;
import com.example.tradingapp.model.TwoFactorAuth;
import com.example.tradingapp.model.User;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user  = userRepository.findByEmail(email);
        if(user == null)
            throw new Exception("User not found");
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user  = userRepository.findByEmail(email);
        if(user == null)
            throw new Exception("User not found");
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new Exception("User not found");
        return user.get();

    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType , String sendTo , User user) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnable(true);
        twoFactorAuth.setSendTo(verificationType);
        user.setTwoFactorAuth(twoFactorAuth);

        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
