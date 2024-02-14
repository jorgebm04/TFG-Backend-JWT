package com.jorgetfg.backend.services.Imp;

import com.jorgetfg.backend.dto.*;
import com.jorgetfg.backend.entities.User;
import com.jorgetfg.backend.exceptions.AppException;
import com.jorgetfg.backend.mappers.IUserMapper;
import com.jorgetfg.backend.repositories.IUserRepository;
import com.jorgetfg.backend.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final IUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;


    public UserDto findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return null;
        }
        return userMapper.toUserDto(optionalUser.get());
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()) {
            return null;
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto login(CredentialDto credentialDto) {
        Optional<User> optionalUser = userRepository.findByEmail(credentialDto.getEmail());

        if (optionalUser.isEmpty()) {
            return null;
        }

        if(passwordEncoder.matches(CharBuffer.wrap(credentialDto.getPassword()),optionalUser.get().getPassword())) {
            return userMapper.toUserDto(optionalUser.get());
        }

        return null;
    }

    public List<CompleteUserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<CompleteUserDto> usersDto = new ArrayList<>();
        for (User u: users) {
            CompleteUserDto completeUser = userMapper.toCompleteUserDto(u);
            usersDto.add(completeUser);
        }
       return usersDto;
    }

    public CompleteUserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = userRepository.findById(userId).get();
        CompleteUserDto completeUser = userMapper.toCompleteUserDto(user);

        return completeUser;
    }

    public CompleteUserDto updateUser(UpdateUserDto updateUser, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = userRepository.findById(userId).get();

        user.setName(updateUser.getName());
        user.setSurname(updateUser.getSurname());
        user.setMessages(updateUser.isMessages());

        return userMapper.toCompleteUserDto(user);
    }

    public CompleteUserDto deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = userRepository.findById(userId).get();
        userRepository.delete(user);
        return userMapper.toCompleteUserDto(user);
    }
}
