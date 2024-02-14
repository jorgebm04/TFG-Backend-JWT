package com.jorgetfg.backend.mappers;

import com.jorgetfg.backend.entities.Folder;
import com.jorgetfg.backend.dto.CompleteFolderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IFolderMapper {

    @Mapping(source = "folderId", target = "folderId")
    @Mapping(source = "name", target = "name")
    @Mapping(target = "subfolders", source = "folder.subfolders")
    @Mapping(target = "subscriptions", source = "folder.subscriptions")
    CompleteFolderDto toCompleteFolderDto(Folder folder);
}
