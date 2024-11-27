package com.example.tradingapp.controller;

import com.example.tradingapp.Repository.UserRepository;
import com.example.tradingapp.config.JwtProvider;
import com.example.tradingapp.model.TwoFactorOtp;
import com.example.tradingapp.model.User;
import com.example.tradingapp.response.AuthResponse;
import com.example.tradingapp.service.CustomerUserDetailsService;
import com.example.tradingapp.service.EmailService;
import com.example.tradingapp.service.TwoFactorOtpService;
import com.example.tradingapp.utils.OtpUtils;
import org.hibernate.mapping.TableOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TwoFactorOtpService twoFactorOtpService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
            throw new Exception("Email is already in use with another account");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());
        newUser.setPassword(user.getPassword());
        User savedUser = userRepository.save(newUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Register success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

        User isEmailExist = userRepository.findByEmail(user.getEmail());
        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);
        User authUser = userRepository.findByEmail(userName);
        if (user.getTwoFactorAuth().isEnable()) {
            AuthResponse res = new AuthResponse();
            res.setMessage("Two facotr auth is enabled");
            res.setTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOtp();
            TwoFactorOtp oldTwoFactorOtp = twoFactorOtpService.findByUser(authUser.getId());
            if (oldTwoFactorOtp != null) {
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOtp);

            }
            TwoFactorOtp newTwoFactorOtp = twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);

            emailService.sendVerificationOtpEmail(userName, otp);
            res.setSession(newTwoFactorOtp.getId());
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);
        if (userDetails == null)
            throw new BadCredentialsException("Invalid Username");
        if (!password.equals(userDetails.getPassword()))
            throw new BadCredentialsException("Invalid Password");

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySignInOtp(@PathVariable String otp, @RequestParam String id) throws Exception {
        TwoFactorOtp twoFactorOtp = twoFactorOtpService.findById(id);
        if (twoFactorOtpService.verifyTwoFactorOtp(twoFactorOtp, otp)) {
            AuthResponse res = new AuthResponse();
            res.setJwt(twoFactorOtp.getJwt());
            res.setStatus(true);
            res.setMessage("Login success");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new Exception("Invalid OTP");
    }
}
