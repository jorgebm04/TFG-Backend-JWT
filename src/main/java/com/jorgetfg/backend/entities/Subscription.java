package com.jorgetfg.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jorgetfg.backend.configuration.AttributeEncryptor;
import jakarta.persistence.*;
import lombok.*;

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
    private Long subscriptionId;

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
    @Convert(converter = AttributeEncryptor.class)
    private String subscriptionUsername;

    @Column(name = "subscription_password")
    @Convert(converter = AttributeEncryptor.class)
    private String subscriptionPassword;

    @Column(name = "subscription_email")
    @Convert(converter = AttributeEncryptor.class)
    private String subscriptionEmail;

    @Column(name = "subscription_bank")
    @Convert(converter = AttributeEncryptor.class)
    private String lastDigitsBank;

    @Column(name = "subscription_comments")
    @Convert(converter = AttributeEncryptor.class)
    private String subscriptionComments;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    @JsonIgnore
    private Folder folder;
}
