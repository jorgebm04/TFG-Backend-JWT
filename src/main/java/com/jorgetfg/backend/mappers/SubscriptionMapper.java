package com.jorgetfg.backend.mappers;

import com.jorgetfg.backend.Entity.Folder;
import com.jorgetfg.backend.Entity.Subscription;
import com.jorgetfg.backend.dto.CompleteSubscriptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "contractDate", target = "contractDate")
    @Mapping(source = "subscriptionFrequency", target = "subscriptionFrequency")
    @Mapping(source = "subscriptionUsername", target = "subscriptionUsername")
    @Mapping(source = "subscriptionPassword", target = "subscriptionPassword")
    @Mapping(source = "subscriptionEmail", target = "subscriptionEmail")
    @Mapping(source = "lastDigitsBank", target = "lastDigitsBank")
    @Mapping(source = "subscriptionComments", target = "subscriptionComments")
    CompleteSubscriptionDto toCompleteSubscriptionDto (Subscription subscription);
}
