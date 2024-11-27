package com.example.tradingapp.service;

import com.example.tradingapp.domain.VerificationType;
import com.example.tradingapp.model.User;
import com.example.tradingapp.model.VerificationCode;
import com.fasterxml.jackson.core.util.VersionUtil;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user , VerificationType verificationType);
    VerificationCode getVerificationCodeById (Long id) throws Exception;
    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCode(VerificationCode verificationCode);

}
