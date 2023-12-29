package com.jorgetfg.backend.services;

import com.jorgetfg.backend.dto.CredentialDto;
import com.jorgetfg.backend.dto.SignUpDto;
import com.jorgetfg.backend.dto.UserDto;
import com.jorgetfg.backend.Entity.User;
import com.jorgetfg.backend.exceptions.AppException;
import com.jorgetfg.backend.mappers.UserMapper;
import com.jorgetfg.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserDto login(CredentialDto credentialDto) {
        User user = userRepository.findByEmail(credentialDto.getEmail())
                .orElseThrow(() -> new AppException("Unknowkn user", HttpStatus.NOT_FOUND));

        if(passwordEncoder.matches(CharBuffer.wrap(credentialDto.getPassword()),user.getPassword())) {
            return userMapper.toUserDto(user);
        }

        throw new AppException("Invalid password",HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new AppException("Email already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }
}
