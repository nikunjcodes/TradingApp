package com.example.tradingapp.controller;

import com.example.tradingapp.request.ForgotPasswordTokenRequest;
import com.example.tradingapp.domain.VerificationType;
import com.example.tradingapp.model.ForgotPasswordToken;
import com.example.tradingapp.model.User;
import com.example.tradingapp.model.VerificationCode;
import com.example.tradingapp.request.ResetPasswordRequest;
import com.example.tradingapp.response.ApiResponse;
import com.example.tradingapp.response.AuthResponse;
import com.example.tradingapp.service.EmailService;
import com.example.tradingapp.service.UserService;
import com.example.tradingapp.service.VerificationCodeService;
import com.example.tradingapp.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tradingapp.service.ForgotPasswordService;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private ForgotPasswordService forgotPasswordService;
    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile( @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        System.out.println("Authorization Header: " + jwt);

        if(user==null)
            throw new Exception("USer not found");
        return new ResponseEntity<>(user, HttpStatus.OK);

    }
    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@PathVariable String otp ,@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
        String sendTo  = verificationCode.getVerificationType().equals(VerificationType.EMAIL) ? verificationCode.getEmail() : verificationCode.getMobile();
        Boolean isVerified = verificationCode.getOtp().equals(otp);
        if(isVerified){
            User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType() , sendTo , user);
            verificationCodeService.deleteVerificationCode(verificationCode);
            return new ResponseEntity<User> (updatedUser , HttpStatus.OK);
        }
        throw new Exception("Invalid OTP");
    }
    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt , @PathVariable VerificationType verificationType) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
        if(verificationCode == null){
           verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }
        if(verificationCode.getVerificationType().equals(VerificationType.EMAIL))
            emailService.sendVerificationOtpEmail(user.getEmail()  , verificationCode.getOtp());

        return new ResponseEntity<String>("Verification code sent successfully" , HttpStatus.OK);

    }
    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp
            (
             @RequestBody ForgotPasswordTokenRequest req) throws Exception {
        User user = userService.findUserByEmail(req.getSendTo());
        String otp = OtpUtils.generateOtp();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());
        if(token == null)
            token = forgotPasswordService.createToken(user , id , req.getSendTo() , req.getVerificationType() , user.getEmail());
        if(req.getVerificationType().equals(VerificationType.EMAIL))
            emailService.sendVerificationOtpEmail(user.getEmail() , token.getOtp());
        AuthResponse response = new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Forgot Password otp sent successfully");
        return new ResponseEntity<>(response , HttpStatus.OK);

    }
    @PatchMapping("/auth/users/reset-password/verify-otp/")
    public ResponseEntity<ApiResponse> resetPasswordOtp(@RequestParam String id ,
                                                 @RequestBody ResetPasswordRequest req,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);
        boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());
        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUser() , req.getPassword());
            ApiResponse res = new ApiResponse();
            res.setMessage("Password Update Successfully");
            return new ResponseEntity<>(res , HttpStatus.OK);
        }
        throw new Exception("Wrong OTP");
    }

}
