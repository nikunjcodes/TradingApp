package com.example.tradingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tradingapp.config.JwtProvider;
import com.example.tradingapp.model.TwoFactorOtp;
import com.example.tradingapp.model.User;
import com.example.tradingapp.Repository.UserRepository;
import com.example.tradingapp.response.AuthResponse;
import com.example.tradingapp.service.CustomerUserDetailsService;
import com.example.tradingapp.service.EmailService;
import com.example.tradingapp.service.TwoFactorOtpService;
import com.example.tradingapp.service.WatchlistService;
import com.example.tradingapp.utils.OtpUtils;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerUserDetailsService customUserDetailsService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
            throw new Exception("email is already used by another account");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(newUser);

        watchlistService.createWatchlist(savedUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("registered successfully");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws MessagingException {
        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(userName, password);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        User authUser = userRepository.findByEmail(userName);

        if (user.getTwoFactorAuth().isEnable()) {
            AuthResponse res = new AuthResponse();
            res.setMessage("Two Factor Authentication is enabled");
            res.setTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOtp();

            TwoFactorOtp oldTwoFactorOTP = twoFactorOtpService.findByUser(authUser.getId());
            if (oldTwoFactorOTP != null) {
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
            }

            TwoFactorOtp newTwoFactorOTP = twoFactorOtpService.createTwoFactorOtp(
                    authUser, otp, jwt
            );

            emailService.sendVerificationOtpEmail(userName, otp);

            res.setSession(newTwoFactorOTP.getId());
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("login successfully");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

        if (userDetails == null) {
            throw new BadCredentialsException("invalid username");
        }

        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySignInOtp(
            @PathVariable String otp,
            @RequestParam String id
    ) throws Exception {
        TwoFactorOtp twoFactorOTP = twoFactorOtpService.findById(id);

        if (twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP, otp)) {
            AuthResponse res = new AuthResponse();
            res.setMessage("Two Factor Authentication verified");
            res.setTwoFactorAuthEnabled(true);
            res.setJwt(twoFactorOTP.getJwt());

            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new Exception("invalid otp");
    }
}