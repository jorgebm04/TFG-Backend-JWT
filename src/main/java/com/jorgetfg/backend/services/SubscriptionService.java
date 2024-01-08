package com.jorgetfg.backend.services;

import com.jorgetfg.backend.Entity.Folder;
import com.jorgetfg.backend.Entity.Subscription;
import com.jorgetfg.backend.Entity.User;
import com.jorgetfg.backend.dto.CompleteFolderDto;
import com.jorgetfg.backend.dto.CompleteSubscriptionDto;
import com.jorgetfg.backend.dto.CreateSubscriptionDto;
import com.jorgetfg.backend.exceptions.AppException;
import com.jorgetfg.backend.mappers.SubscriptionMapper;
import com.jorgetfg.backend.repositories.FolderRepository;
import com.jorgetfg.backend.repositories.SubscriptionRepository;
import com.jorgetfg.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    private final FolderService folderService;
    private final SubscriptionMapper subscriptionMapper;

    public Set<CompleteSubscriptionDto> getUserSubscriptions(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Set<Subscription> userSubscriptions = new HashSet<>();
        Set<CompleteSubscriptionDto> userCompleteSubscription = new HashSet<>();

        if (optionalUser.isEmpty()) {
            throw new AppException("No user with that id", HttpStatus.BAD_REQUEST);
        }

        List<CompleteFolderDto> userFolders = folderService.getUserFolders(userId);

        for (CompleteFolderDto c: userFolders) {
            for (Subscription s: c.getSubscriptions()) {
                userSubscriptions.add(s);
            }
        }

        for (Subscription s: userSubscriptions){
            userCompleteSubscription.add(subscriptionMapper.toCompleteSubscriptionDto(s));
        }

        return userCompleteSubscription;
    }

    public CompleteSubscriptionDto getUserSubscription(Long userId, Long subscriptionId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(subscriptionId);

        if (optionalUser.isEmpty()) {
            throw new AppException("No user with that id", HttpStatus.BAD_REQUEST);
        }

        if (optionalSubscription.isEmpty()) {
            throw new AppException("No subscription with that id", HttpStatus.BAD_REQUEST);
        }

        Subscription subscription = optionalSubscription.get();

        return subscriptionMapper.toCompleteSubscriptionDto(subscription);
    }

    public CompleteSubscriptionDto addUserSubscription(Long userId, Long folderId, CreateSubscriptionDto subscriptionDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);

        if (optionalUser.isEmpty()) {
            throw new AppException("No user with that id", HttpStatus.BAD_REQUEST);
        }

        if (optionalFolder.isEmpty()) {
            throw new AppException("No folder with that id", HttpStatus.BAD_REQUEST);
        }

        Subscription subscription = new Subscription(
                null, //SubscriptionId
                subscriptionDto.getName(),
                subscriptionDto.getPrice(),
                subscriptionDto.getContractDate(),
                subscriptionDto.getSubscriptionFrequency(),
                subscriptionDto.getSubscriptionUsername(),
                subscriptionDto.getSubscriptionPassword(),
                subscriptionDto.getSubscriptionEmail(),
                subscriptionDto.getLastDigitsBank(),
                subscriptionDto.getSubscriptionComments(),
                optionalFolder.get()
        );

        subscriptionRepository.save(subscription);

        return subscriptionMapper.toCompleteSubscriptionDto(subscription);
    }

    public CompleteSubscriptionDto updateUserSubscription(Long userId, Long folderId, Long subscriptionId,
                                                          CreateSubscriptionDto subscriptionDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(subscriptionId);

        if (optionalUser.isEmpty()) {
            throw new AppException("No user with that id", HttpStatus.BAD_REQUEST);
        }

        if (optionalFolder.isEmpty()) {
            throw new AppException("No folder with that id", HttpStatus.BAD_REQUEST);
        }

        if (optionalSubscription.isEmpty()) {
            throw new AppException("No subscription with that id", HttpStatus.BAD_REQUEST);
        }

        Subscription subscription = optionalSubscription.get();
        subscription.setName(subscriptionDto.getName());
        subscription.setPrice(subscriptionDto.getPrice());
        subscription.setContractDate(subscriptionDto.getContractDate());
        subscription.setSubscriptionFrequency(subscriptionDto.getSubscriptionFrequency());
        subscription.setSubscriptionUsername(subscriptionDto.getSubscriptionUsername());
        subscription.setSubscriptionPassword(subscriptionDto.getSubscriptionPassword());
        subscription.setSubscriptionEmail(subscriptionDto.getSubscriptionEmail());
        subscription.setSubscriptionComments(subscriptionDto.getSubscriptionComments());
        subscription.setLastDigitsBank(subscriptionDto.getLastDigitsBank());
        subscription.setFolder(optionalFolder.get());

        subscriptionRepository.save(subscription);

        return subscriptionMapper.toCompleteSubscriptionDto(subscription);
    }

    public CompleteSubscriptionDto deleteUserSubscription(Long userId, Long folderId, Long subscriptionId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(subscriptionId);

        if (optionalUser.isEmpty()) {
            throw new AppException("No user with that id", HttpStatus.BAD_REQUEST);
        }

        if (optionalFolder.isEmpty()) {
            throw new AppException("No folder with that id", HttpStatus.BAD_REQUEST);
        }

        if (optionalSubscription.isEmpty()) {
            throw new AppException("No subscription with that id", HttpStatus.BAD_REQUEST);
        }

        subscriptionRepository.delete(optionalSubscription.get());

        return subscriptionMapper.toCompleteSubscriptionDto(optionalSubscription.get());
    }
}
