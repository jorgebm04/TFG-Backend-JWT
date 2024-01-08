package com.jorgetfg.backend.mappers;

import com.jorgetfg.backend.dto.CompleteUserDto;
import com.jorgetfg.backend.dto.SignUpDto;
import com.jorgetfg.backend.dto.UserDto;
import com.jorgetfg.backend.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source="email", target = "email")
    @Mapping(source = "userId", target = "id")
    UserDto toUserDto(User user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "messages", target = "messages")
    User signUpToUser(SignUpDto userDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "messages", target = "messages")
    @Mapping(source = "folders", target = "folders")
    CompleteUserDto toCompleteUserDto(User user);

}
