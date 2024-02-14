package com.jorgetfg.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CompleteSubscriptionDto {

    private Long subscriptionId;
    private String name;
    private double price;
    private String contractDate;
    private int subscriptionFrequency;
    private String subscriptionUsername;
    private String subscriptionPassword;
    private String subscriptionEmail;
    private String lastDigitsBank;
    private String subscriptionComments;
    private Long folderId;
}
