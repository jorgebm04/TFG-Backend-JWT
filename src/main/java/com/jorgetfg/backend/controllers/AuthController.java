package com.jorgetfg.backend.controllers;

import com.jorgetfg.backend.configuration.UserAuthProvider;
import com.jorgetfg.backend.dto.CredentialDto;
import com.jorgetfg.backend.dto.SignUpDto;
import com.jorgetfg.backend.dto.UserDto;
import com.jorgetfg.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialDto credentialDto){
        UserDto user = userService.login(credentialDto);

        user.setToken(userAuthProvider.createToken(user.getEmail()));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user.getEmail()));
        return ResponseEntity.created(URI.create("/users/" + user.getId()))
                .body(user);
    }
}
