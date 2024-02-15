package com.jorgetfg.backend.controllers;

import com.jorgetfg.backend.dto.CompleteSubscriptionDto;
import com.jorgetfg.backend.dto.CreateSubscriptionDto;
import com.jorgetfg.backend.services.Imp.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping(path = "/users/{userId}/subscriptions")
    public ResponseEntity<List<CompleteSubscriptionDto>> getUserSubscriptions(@PathVariable Long userId) {
        List<CompleteSubscriptionDto> completeSubscriptionDtoList = subscriptionService.getUserSubscriptions(userId);
        if (completeSubscriptionDtoList == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(userId));
    }

    @GetMapping(path = "/users/{userId}/subscriptions/{subscriptionId}")
    public ResponseEntity<CompleteSubscriptionDto> getUserSubscription(@PathVariable Long userId,
                                                                       @PathVariable Long subscriptionId) {
        CompleteSubscriptionDto completeSubscriptionDto =  subscriptionService.getUserSubscription(userId,subscriptionId);
        if (completeSubscriptionDto == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscriptionService.getUserSubscription(userId,subscriptionId));
    }

    @PostMapping(path = "/users/{userId}/folders/{folderId}/subscriptions")
    public ResponseEntity<CompleteSubscriptionDto> addUserSubscription(@PathVariable Long userId,
                                                                       @PathVariable Long folderId,
                                                                       @RequestBody CreateSubscriptionDto createSubscriptionDto) {
        CompleteSubscriptionDto completeSubscriptionDto = subscriptionService.addUserSubscription(userId,folderId,
                createSubscriptionDto);
        if (completeSubscriptionDto == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscriptionService.addUserSubscription(userId,folderId,createSubscriptionDto));
    }

    @PutMapping(path = "/users/{userId}/folders/{folderId}/subscriptions/{subscriptionId}")
    public ResponseEntity<CompleteSubscriptionDto> updateUserSubscription(@PathVariable Long userId,
                                                                          @PathVariable Long folderId,
                                                                          @PathVariable Long subscriptionId,
                                                                          @RequestBody CreateSubscriptionDto createSubscriptionDto) {
        CompleteSubscriptionDto completeSubscriptionDto = subscriptionService.updateUserSubscription(userId,folderId,
                subscriptionId,createSubscriptionDto);
        if (completeSubscriptionDto == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscriptionService.updateUserSubscription(userId,folderId,subscriptionId,createSubscriptionDto));
    }

    @DeleteMapping(path = "/users/{userId}/folders/{folderId}/subscriptions/{subscriptionId}")
    public ResponseEntity<CompleteSubscriptionDto> deleteUserSubscription(@PathVariable Long userId,
                                                                          @PathVariable Long folderId,
                                                                          @PathVariable Long subscriptionId) {
        CompleteSubscriptionDto completeSubscriptionDto = subscriptionService.deleteUserSubscription(userId,folderId,subscriptionId);
        if (completeSubscriptionDto == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscriptionService.deleteUserSubscription(userId,folderId,subscriptionId));
    }
}
