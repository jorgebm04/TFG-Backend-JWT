package com.jorgetfg.backend.services;

import com.jorgetfg.backend.dto.CompleteFolderDto;
import com.jorgetfg.backend.dto.CreateFolderDto;

import java.util.List;

public interface IFolderService {
    List<CompleteFolderDto> getUserFolders(Long userId);
    CompleteFolderDto getUserFolder(Long userId, Long folderId);
    CompleteFolderDto addUserFolder(Long userId, CreateFolderDto createFolder);
    CompleteFolderDto updateUserFolder(Long userId, Long folderId, CreateFolderDto createFolder);
    CompleteFolderDto deleteUserFolder (Long userId, Long folderId);
}
