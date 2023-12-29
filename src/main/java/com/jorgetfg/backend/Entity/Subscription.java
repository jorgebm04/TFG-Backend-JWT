package com.jorgetfg.backend.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @Column(name = "subscription_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subscriptionId;

    @Column(name = "subscription_name",nullable = false,length = 255)
    private String name;

    @Column(name = "subscription_price",nullable = false)
    private double price;

    @Column(name = "contract_date",nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",locale = "es_ES")
    private Date contractDate;

    @Column(name="subscription_frequency",nullable = false)
    private int subscriptionFrequency;

    @Column(name = "subscription_username")
    private String subscriptionUsername;

    @Column(name = "subscription_password")
    private String subscriptionPassword;

    @Column(name = "subscription_email")
    private String subscriptionEmail;

    @Column(name = "subscription_bank")
    private String lastDigitsBank;

    @Column(name = "subscription_comments")
    private String subscriptionComments;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "folder_subscription",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "folder_id"))
    @JsonIgnore
    private Folder folder;
}
