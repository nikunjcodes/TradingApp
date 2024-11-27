package com.example.tradingapp.model;

import com.example.tradingapp.domain.VerificationType;
import lombok.Data;
@Data
public class TwoFactorAuth {
    private boolean isEnable = false;
    private VerificationType sendTo;
}
