package com.jorgetfg.backend.services;

import com.jorgetfg.backend.Entity.Folder;
import com.jorgetfg.backend.Entity.Subscription;
import com.jorgetfg.backend.Entity.User;
import com.jorgetfg.backend.dto.CompleteFolderDto;
import com.jorgetfg.backend.dto.CreateFolderDto;
import com.jorgetfg.backend.exceptions.AppException;
import com.jorgetfg.backend.mappers.FolderMapper;
import com.jorgetfg.backend.repositories.FolderRepository;
import com.jorgetfg.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;
    private final FolderMapper folderMapper;

    public List<CompleteFolderDto> getUserFolders(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new AppException("No user with that id", HttpStatus.BAD_REQUEST);
        }

        List<Folder> folders = userRepository.findById(userId).get().getFolders();

        Set<Long> visitedFolderIds = new HashSet<>();
        List<Folder> flattenedFolders = folders.stream()
                .flatMap(folder -> flattenFolder(folder, visitedFolderIds).stream())
                .collect(Collectors.toList());

        List<CompleteFolderDto> completeFolders = new ArrayList<>();
        for (Folder f: flattenedFolders) {
            CompleteFolderDto completeFolder = folderMapper.toCompleteFolderDto(f);
            completeFolders.add(completeFolder);
        }
        return completeFolders;
    }

    private List<Folder> flattenFolder(Folder folder, Set<Long> visitedFolderIds) {
        if (visitedFolderIds.contains(folder.getFolderId())) {
            return Collections.emptyList();
        } else {
            visitedFolderIds.add(folder.getFolderId());
            List<Folder> subFolders = folder.getSubfolders().stream()
                    .flatMap(subFolder -> flattenFolder(subFolder, visitedFolderIds).stream())
                    .collect(Collectors.toList());
            Folder flattenedFolder = new Folder(
                    folder.getFolderId(),
                    folder.getName(),
                    folder.getParentFolder(),
                    subFolders,
                    folder.getSubscriptions(),
                    folder.getUser()
            );
            return Collections.singletonList(flattenedFolder);
        }
    }

    public CompleteFolderDto getUserFolder(Long userId, Long folderId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new AppException("No user with that id", HttpStatus.BAD_REQUEST);
        }

        Optional<Folder> optionalFolder = folderRepository.findById(folderId);

        if (optionalFolder.isEmpty()) {
            throw new AppException("No folder with that id", HttpStatus.BAD_REQUEST);
        }

        Folder folder = optionalFolder.get();

        return folderMapper.toCompleteFolderDto(folder);
    }

    public CompleteFolderDto addUserFolder(Long userId, CreateFolderDto createFolder) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Folder> optionalparentFolder = null;

        if (optionalUser.isEmpty()) {
            throw new AppException("No user with that id", HttpStatus.BAD_REQUEST);
        }

        if (createFolder.getParentFolderId()!=null){
            optionalparentFolder = folderRepository.findById(createFolder.getParentFolderId());

            if (optionalparentFolder.isEmpty()) {
                throw new AppException("No folder with that id", HttpStatus.BAD_REQUEST);
            }
        }

        Folder folder = new Folder(
                null, //Id
                createFolder.getName(),
                optionalparentFolder == null ? null : optionalparentFolder.get(),
                null, //subfolders
                null, //subscriptions
                optionalUser.get()
        );

        folder = folderRepository.save(folder);
        CompleteFolderDto completeFolder = folderMapper.toCompleteFolderDto(folder);
        return completeFolder;
    }

    public CompleteFolderDto updateUserFolder(Long userId, Long folderId, CreateFolderDto createFolder) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);
        Optional<Folder> optionalParentFolder = null;

        if (optionalUser.isEmpty()) {
            throw new AppException("No user with that id", HttpStatus.BAD_REQUEST);
        }

        if (optionalFolder.isEmpty()) {
            throw new AppException("No folder with that id", HttpStatus.BAD_REQUEST);
        }

        if (createFolder.getParentFolderId()!=null){
            optionalParentFolder = folderRepository.findById(createFolder.getParentFolderId());

            if (optionalParentFolder.isEmpty()) {
                throw new AppException("No folder with that id", HttpStatus.BAD_REQUEST);
            }
        }

        Folder folderToUpdate = optionalFolder.get();

        folderToUpdate.setName(createFolder.getName());
        folderToUpdate.setParentFolder(optionalParentFolder.get());

        folderRepository.save(folderToUpdate);

        return folderMapper.toCompleteFolderDto(folderToUpdate);
    }

    public CompleteFolderDto deleteUserFolder (Long userId, Long folderId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);

        if (optionalUser.isEmpty()) {
            throw new AppException("No user with that id", HttpStatus.BAD_REQUEST);
        }

        if (optionalFolder.isEmpty()) {
            throw new AppException("No folder with that id", HttpStatus.BAD_REQUEST);
        }

        folderRepository.delete(optionalFolder.get());
        return folderMapper.toCompleteFolderDto(optionalFolder.get());
    }
}
