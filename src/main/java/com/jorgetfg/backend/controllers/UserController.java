package com.jorgetfg.backend.controllers;

import com.jorgetfg.backend.dto.CompleteUserDto;
import com.jorgetfg.backend.dto.UpdateUserDto;
import com.jorgetfg.backend.services.Imp.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/users")
    public ResponseEntity<List<CompleteUserDto>> getAllUsers(){return ResponseEntity.ok(userService.getAllUsers());}

    @GetMapping(path = "/users/{userId}")
    public ResponseEntity<CompleteUserDto> getUserById(@PathVariable Long userId){
        CompleteUserDto completeUserDto = userService.getUserById(userId);
        if (completeUserDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(completeUserDto);
    }

    @PutMapping(path = "/user/{userId}")
    public ResponseEntity<CompleteUserDto> updateUser(@RequestBody UpdateUserDto updateUser,
                                                      @PathVariable Long userId) {
        CompleteUserDto completeUserDto = userService.updateUser(updateUser,userId);
        if (completeUserDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.updateUser(updateUser,userId));
    }

    @DeleteMapping(path = "user/{userId}")
    public ResponseEntity<CompleteUserDto> deleteUser(@PathVariable Long userId){
        CompleteUserDto completeUserDto = userService.deleteUser(userId);
        if (completeUserDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
