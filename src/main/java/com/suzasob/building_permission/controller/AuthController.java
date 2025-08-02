package com.suzasob.building_permission.controller;

import com.suzasob.building_permission.dto.AuthRequestDTO;
import com.suzasob.building_permission.dto.RegisterRequestDTO;
import com.suzasob.building_permission.model.User;
import com.suzasob.building_permission.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto) {
        User savedUser = userService.registerUser(dto);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO dto) {
        Optional<User> user = userService.authenticateUser(dto);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
