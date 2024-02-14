package com.jorgetfg.backend.mappers;

import com.jorgetfg.backend.entities.Folder;
import com.jorgetfg.backend.entities.Subscription;
import com.jorgetfg.backend.dto.CompleteSubscriptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ISubscriptionMapper {

    @Mapping(source = "subscriptionId", target = "subscriptionId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "contractDate", target = "contractDate")
    @Mapping(source = "subscriptionFrequency", target = "subscriptionFrequency")
    @Mapping(source = "subscriptionUsername", target = "subscriptionUsername")
    @Mapping(source = "subscriptionPassword", target = "subscriptionPassword")
    @Mapping(source = "subscriptionEmail", target = "subscriptionEmail")
    @Mapping(source = "lastDigitsBank", target = "lastDigitsBank")
    @Mapping(source = "subscriptionComments", target = "subscriptionComments")
    @Mapping(source = "folder", target = "folderId", qualifiedByName = "folderToId")
    CompleteSubscriptionDto toCompleteSubscriptionDto (Subscription subscription);

    @Named("folderToId")
    static Long folderToId(Folder folder) {
        return folder != null ? folder.getFolderId() : null;
    }
}
