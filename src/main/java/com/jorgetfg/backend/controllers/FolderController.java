package com.jorgetfg.backend.controllers;

import com.jorgetfg.backend.dto.CompleteFolderDto;
import com.jorgetfg.backend.dto.CreateFolderDto;
import com.jorgetfg.backend.services.Imp.FolderService;
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
        List<CompleteFolderDto> completeFolderDtoList= folderService.getUserFolders(userId);
        if (completeFolderDtoList == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(folderService.getUserFolders(userId));
    }

    @GetMapping(path = "users/{userId}/folders/{folderId}")
    public ResponseEntity<CompleteFolderDto> getUserFolder (@PathVariable Long userId, @PathVariable Long folderId) {
        CompleteFolderDto completeFolderDto = folderService.getUserFolder(userId,folderId);
        if (completeFolderDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(folderService.getUserFolder(userId,folderId));
    }

    @PostMapping(path = "/users/{userId}/folders")
    public ResponseEntity<CompleteFolderDto> saveUserFolder(@RequestBody CreateFolderDto createFolderDto, @PathVariable Long userId){
        CompleteFolderDto completeFolderDto = folderService.addUserFolder(userId,createFolderDto);
        if (completeFolderDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(folderService.addUserFolder(userId,createFolderDto));
    }

    @PutMapping(path = "/users/{userId}/folders/{folderId}")
    public ResponseEntity<CompleteFolderDto> updateUserFolder (@RequestBody CreateFolderDto createFolderDto, @PathVariable Long userId,
                                                               @PathVariable Long folderId){
        CompleteFolderDto completeFolderDto = folderService.updateUserFolder(userId,folderId,createFolderDto);
        if (completeFolderDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(folderService.updateUserFolder(userId,folderId,createFolderDto));
    }

    @DeleteMapping(path = "/users/{userId}/folders/{folderId}")
    public ResponseEntity<CompleteFolderDto> deleteUserFolder (@PathVariable Long userId, @PathVariable Long folderId) {
        CompleteFolderDto completeFolderDto = folderService.deleteUserFolder(userId,folderId);
        if (completeFolderDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(folderService.deleteUserFolder(userId,folderId));
    }
}
