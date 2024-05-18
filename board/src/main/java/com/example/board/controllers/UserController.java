package com.example.board.controllers;

import com.example.board.models.User;
import com.example.board.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    public ResponseEntity<String> signInUser(
            @RequestParam String username,
            @RequestParam String password
    ) {
        boolean isAuthenticated = userService.authenticateUser(username, password);
        if (isAuthenticated) {
            return ResponseEntity.ok("User signed in successfully!");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials!");
        }
    }
}
