package com.jorgetfg.backend.controllers;

import com.jorgetfg.backend.configuration.UserAuthProvider;
import com.jorgetfg.backend.dto.CredentialDto;
import com.jorgetfg.backend.dto.SignUpDto;
import com.jorgetfg.backend.dto.UserDto;
import com.jorgetfg.backend.services.Imp.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/token")
    public ResponseEntity<UserDto> getToken(@RequestBody @Valid CredentialDto credentialDto, BindingResult result){
        if (result.hasErrors()) {
            // Return a 400 Bad Request response if validation fails
            return ResponseEntity.badRequest().build();
        }
        UserDto user = userService.login(credentialDto);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setToken(userAuthProvider.createToken(user.getEmail()));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto signUpDto, BindingResult result) {
        if (result.hasErrors()) {
            // Return a 400 Bad Request response if validation fails
            return ResponseEntity.badRequest().build();
        }
        UserDto user = userService.register(signUpDto);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setToken(userAuthProvider.createToken(user.getEmail()));
        return ResponseEntity.created(URI.create("/users/" + user.getId()))
                .body(user);
    }
}
