package com.jorgetfg.backend.mappers;

import com.jorgetfg.backend.dto.SignUpDto;
import com.jorgetfg.backend.dto.UserDto;
import com.jorgetfg.backend.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "messages", target = "messages")
    User signUpToUser(SignUpDto userDto);
}
