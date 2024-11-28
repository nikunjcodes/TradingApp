package com.example.tradingapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import com.example.tradingapp.model.User;
@Data
@Entity
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    private String accountNumber;

    private String accountHolderName;

    private String ifsc;

    private String bankName;
    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

}
