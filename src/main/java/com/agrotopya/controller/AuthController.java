package com.agrotopya.controller;

import com.agrotopya.dto.LoginRequest;
import com.agrotopya.dto.LoginResponse;
import com.agrotopya.model.User;
import com.agrotopya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userService.getUserByUsername(loginRequest.getUsername());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // In a real application, you would use proper password hashing and verification
            if (user.getPassword().equals(loginRequest.getPassword())) {
                LoginResponse response = new LoginResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getFullName(),
                    "dummy-jwt-token", // In a real app, generate a proper JWT token
                    true,
                    "Login successful"
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        
        LoginResponse response = new LoginResponse(
            null,
            null,
            null,
            null,
            false,
            "Invalid username or password"
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            LoginResponse response = new LoginResponse(
                null,
                null,
                null,
                null,
                false,
                "Username already exists"
            );
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        
        if (userService.existsByEmail(user.getEmail())) {
            LoginResponse response = new LoginResponse(
                null,
                null,
                null,
                null,
                false,
                "Email already exists"
            );
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        
        User createdUser = userService.createUser(user);
        LoginResponse response = new LoginResponse(
            createdUser.getId(),
            createdUser.getUsername(),
            createdUser.getFullName(),
            "dummy-jwt-token", // In a real app, generate a proper JWT token
            true,
            "Registration successful"
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
