package com.jorgetfg.backend.controllers;

import com.jorgetfg.backend.dto.CompleteFolderDto;
import com.jorgetfg.backend.dto.CreateFolderDto;
import com.jorgetfg.backend.services.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FolderController {

    private final FolderService folderService;

    @GetMapping(path = "/users/{userId}/folders")
    public ResponseEntity<List<CompleteFolderDto>> getUserFolders(@PathVariable Long userId){
        return ResponseEntity.ok(folderService.getUserFolders(userId));
    }

    @GetMapping(path = "users/{userId}/folders/{folderId}")
    public ResponseEntity<CompleteFolderDto> getUserFolder (@PathVariable Long userId, @PathVariable Long folderId) {
        return ResponseEntity.ok(folderService.getUserFolder(userId,folderId));
    }

    @PostMapping(path = "/users/{userId}/folders")
    public ResponseEntity<CompleteFolderDto> saveUserFolder(@RequestBody CreateFolderDto createFolderDto, @PathVariable Long userId){
        return ResponseEntity.ok(folderService.addUserFolder(userId,createFolderDto));
    }

    @PutMapping(path = "/users/{userId}/folders/{folderId}")
    public ResponseEntity<CompleteFolderDto> updateUserFolder (@RequestBody CreateFolderDto createFolderDto, @PathVariable Long userId,
                                                               @PathVariable Long folderId){
        return ResponseEntity.ok(folderService.updateUserFolder(userId,folderId,createFolderDto));
    }

    @DeleteMapping(path = "/users/{userId}/folders/{folderId}")
    public ResponseEntity<CompleteFolderDto> deleteUserFolder (@PathVariable Long userId, @PathVariable Long folderId) {
        return ResponseEntity.ok(folderService.deleteUserFolder(userId,folderId));
    }
}
