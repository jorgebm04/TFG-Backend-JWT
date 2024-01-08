package com.jorgetfg.backend.mappers;

import com.jorgetfg.backend.Entity.Folder;
import com.jorgetfg.backend.dto.CompleteFolderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FolderMapper {

    @Mapping(source = "folderId", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(target = "subfolders", source = "folder.subfolders")
    @Mapping(target = "subscriptions", source = "folder.subscriptions")
    CompleteFolderDto toCompleteFolderDto(Folder folder);
}
