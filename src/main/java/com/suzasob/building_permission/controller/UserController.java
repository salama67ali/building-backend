package com.suzasob.building_permission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suzasob.building_permission.model.User;
import com.suzasob.building_permission.model.UserRole;
import com.suzasob.building_permission.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "API for managing users in the building permissions system")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user in the system with specified role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided"),
            @ApiResponse(responseCode = "409", description = "User with this email already exists")
    })
    public ResponseEntity<User> register(
            @Parameter(description = "User information to register", required = true) @RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role", description = "Retrieve all users with a specific role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role specified")
    })
    public ResponseEntity<List<User>> getByRole(
            @Parameter(description = "User role to filter by", required = true, example = "ENGINEER") @PathVariable UserRole role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }
}
