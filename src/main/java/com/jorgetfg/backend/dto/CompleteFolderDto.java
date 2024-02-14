package com.jorgetfg.backend.dto;

import com.jorgetfg.backend.entities.Folder;
import com.jorgetfg.backend.entities.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CompleteFolderDto {
    private Long folderId;
    private String name;
    private List<Folder> subfolders;
    private List<Subscription> subscriptions;
}
