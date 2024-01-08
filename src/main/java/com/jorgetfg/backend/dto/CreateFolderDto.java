package com.jorgetfg.backend.dto;

import com.jorgetfg.backend.Entity.Folder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateFolderDto {
    private String name;
    private Long parentFolderId;
}
