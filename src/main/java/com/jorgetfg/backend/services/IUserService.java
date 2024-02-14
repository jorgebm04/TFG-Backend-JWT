package com.jorgetfg.backend.services;

import com.jorgetfg.backend.dto.*;

import java.util.List;

public interface IUserService {
    UserDto findByEmail(String email);
    UserDto login(CredentialDto credentialDto);
    UserDto register(SignUpDto userDto);
    List<CompleteUserDto> getAllUsers();
    CompleteUserDto getUserById(Long userId);
    CompleteUserDto updateUser(UpdateUserDto updateUser, Long userId);
    CompleteUserDto deleteUser(Long userId);
}
