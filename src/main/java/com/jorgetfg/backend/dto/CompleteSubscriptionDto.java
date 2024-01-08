package com.jorgetfg.backend.dto;

import com.jorgetfg.backend.Entity.Folder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CompleteSubscriptionDto {

    private String name;
    private double price;
    private Date contractDate;
    private int subscriptionFrequency;
    private String subscriptionUsername;
    private String subscriptionPassword;
    private String subscriptionEmail;
    private String lastDigitsBank;
    private String subscriptionComments;
}