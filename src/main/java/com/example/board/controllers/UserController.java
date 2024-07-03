package com.example.board.controllers;

import com.example.board.models.SignInRequestDto;
import com.example.board.models.User;
import com.example.board.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(
            @RequestBody User user
    ) {
        boolean isUserCreated = userService.createUser(user);
        if (isUserCreated) {
            return ResponseEntity.ok("User created successfully!");
        } else {
            return ResponseEntity.badRequest().body("User creation failed!");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> signInUser(
            @Valid @RequestBody SignInRequestDto requestDto
            ) {
        String token = this.userService.authenticateUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(token);

    }
}
