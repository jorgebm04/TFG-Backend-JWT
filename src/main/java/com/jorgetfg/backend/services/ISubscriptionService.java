package com.jorgetfg.backend.services;

import com.jorgetfg.backend.dto.CompleteSubscriptionDto;
import com.jorgetfg.backend.dto.CreateSubscriptionDto;

import java.util.List;

public interface ISubscriptionService {
    List<CompleteSubscriptionDto> getUserSubscriptions(Long userId);
    CompleteSubscriptionDto getUserSubscription(Long userId, Long subscriptionId);
    CompleteSubscriptionDto addUserSubscription(Long userId, Long folderId, CreateSubscriptionDto subscriptionDto);
    CompleteSubscriptionDto updateUserSubscription(Long userId, Long folderId, Long subscriptionId,
                                                   CreateSubscriptionDto subscriptionDto);
    CompleteSubscriptionDto deleteUserSubscription(Long userId, Long folderId, Long subscriptionId);
}
