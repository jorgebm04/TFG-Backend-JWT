package com.jorgetfg.backend.services.Imp;

import com.jorgetfg.backend.entities.Folder;
import com.jorgetfg.backend.entities.Subscription;
import com.jorgetfg.backend.entities.User;
import com.jorgetfg.backend.dto.CompleteFolderDto;
import com.jorgetfg.backend.dto.CompleteSubscriptionDto;
import com.jorgetfg.backend.dto.CreateSubscriptionDto;
import com.jorgetfg.backend.exceptions.AppException;
import com.jorgetfg.backend.mappers.IFolderMapper;
import com.jorgetfg.backend.mappers.ISubscriptionMapper;
import com.jorgetfg.backend.repositories.IFolderRepository;
import com.jorgetfg.backend.repositories.ISubscriptionRepository;
import com.jorgetfg.backend.repositories.IUserRepository;
import com.jorgetfg.backend.services.ISubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class SubscriptionService implements ISubscriptionService {

    private final ISubscriptionRepository subscriptionRepository;
    private final IFolderRepository folderRepository;
    private final IUserRepository userRepository;
    private final FolderService folderService;
    private final ISubscriptionMapper subscriptionMapper;
    private final IFolderMapper folderMapper;

    public List<CompleteSubscriptionDto> getUserSubscriptions(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        List<Subscription> userSubscriptions = new ArrayList<>();
        List<CompleteSubscriptionDto> userCompleteSubscription = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (optionalUser.isEmpty()) {
            return null;
        }

        List<CompleteFolderDto> userFolders = folderService.getUserFolders(userId);

        for (CompleteFolderDto c: userFolders) {
            addSubfoldersSubscriptions(c,userSubscriptions);
            userSubscriptions.addAll(c.getSubscriptions());
        }

        for (Subscription s: userSubscriptions){
            CompleteSubscriptionDto completeSubscriptionDto = subscriptionMapper.toCompleteSubscriptionDto(s);
            completeSubscriptionDto.setContractDate(simpleDateFormat.format(s.getContractDate()));
            userCompleteSubscription.add(completeSubscriptionDto);
        }

        return userCompleteSubscription;
    }

    private void addSubfoldersSubscriptions(CompleteFolderDto folder, List<Subscription> subscriptions) {
        for (Folder subfolder: folder.getSubfolders()) {
            CompleteFolderDto completeSubFolder = folderMapper.toCompleteFolderDto(subfolder);
            addSubfoldersSubscriptions(completeSubFolder,subscriptions);
            subscriptions.addAll(subfolder.getSubscriptions());
        }
    }

    public CompleteSubscriptionDto getUserSubscription(Long userId, Long subscriptionId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(subscriptionId);

        if (optionalUser.isEmpty()) {
            return null;
        }

        if (optionalSubscription.isEmpty()) {
            return null;
        }

        Subscription subscription = optionalSubscription.get();

        return subscriptionMapper.toCompleteSubscriptionDto(subscription);
    }

    public CompleteSubscriptionDto addUserSubscription(Long userId, Long folderId, CreateSubscriptionDto subscriptionDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);

        if (optionalUser.isEmpty()) {
            return null;
        }

        if (optionalFolder.isEmpty()) {
            return null;
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
            return null;
        }

        if (optionalFolder.isEmpty()) {
            return null;
        }

        if (optionalSubscription.isEmpty()) {
            return null;
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

    @Transactional
    public CompleteSubscriptionDto deleteUserSubscription(Long userId, Long folderId, Long subscriptionId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(subscriptionId);

        if (optionalUser.isEmpty()) {
            return null;
        }

        if (optionalFolder.isEmpty()) {
            return null;
        }

        if (optionalSubscription.isEmpty()) {
            return null;
        }

        subscriptionRepository.deleteById(subscriptionId);

        return subscriptionMapper.toCompleteSubscriptionDto(optionalSubscription.get());
    }
}
