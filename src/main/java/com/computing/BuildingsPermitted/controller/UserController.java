package com.computing.BuildingsPermitted.controller;

import com.computing.BuildingsPermitted.dto.AuthRequest;
import com.computing.BuildingsPermitted.dto.AuthResponse;
import com.computing.BuildingsPermitted.dto.UserRegistrationRequest;
import com.computing.BuildingsPermitted.dto.UpdateUserRequest;
import com.computing.BuildingsPermitted.dto.ResetPasswordRequest;
import com.computing.BuildingsPermitted.dto.UserDTO;
import com.computing.BuildingsPermitted.model.User;
import com.computing.BuildingsPermitted.repository.UserRepository;
import com.computing.BuildingsPermitted.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // JWT Authentication endpoints
    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> registerWithJwt(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, null, null, null, e.getMessage()));
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> loginWithJwt(@Valid @RequestBody AuthRequest request) {
        try {
            AuthResponse response = authService.login(request.getEmail(), request.getPassword(), request.getRole());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, null, null, null, e.getMessage()));
        }
    }

    // Other endpoints (users CRUD, dashboards) remain the same
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size));
        Page<User> usersPage;
        if (role != null && !role.isBlank()) {
            usersPage = userRepository.findByRole(role, pageable);
        } else if (email != null && !email.isBlank()) {
            usersPage = userRepository.findByEmailContainingIgnoreCase(email, pageable);
        } else {
            usersPage = userRepository.findAll(pageable);
        }
        List<UserDTO> items = usersPage.getContent().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
                "items", items,
                "page", usersPage.getNumber(),
                "size", usersPage.getSize(),
                "totalElements", usersPage.getTotalElements(),
                "totalPages", usersPage.getTotalPages()
        ));
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        String email = user.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already exists."));
        }
        user.setEmail(email);
        authService.register(new UserRegistrationRequest(user.getUsername(), user.getEmail(), user.getPassword(), user.getRole()));
        return ResponseEntity.ok(Map.of("message", "User added successfully."));
    }

    // Admin: get user by id
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.<ResponseEntity<?>>map(u -> ResponseEntity.ok(toDto(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Admin: update user (username, email, role)
    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest req) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();

        String email = req.getEmail().trim().toLowerCase();
        // if email changed, ensure uniqueness
        if (!email.equals(user.getEmail()) && userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already exists."));
        }

        user.setUsername(req.getUsername());
        user.setEmail(email);
        user.setRole(req.getRole());
        userRepository.save(user);
        return ResponseEntity.ok(toDto(user));
    }

    // Admin: change role only
    @PatchMapping("/users/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeRole(@PathVariable Long id, @RequestParam String role) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        user.setRole(role);
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Role updated."));
    }

    // Admin: reset password
    @PostMapping("/users/{id}/reset-password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> resetPassword(@PathVariable Long id, @Valid @RequestBody ResetPasswordRequest req) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Password reset."));
    }

    // Admin: delete user
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UserDTO toDto(User u) {
        return new UserDTO(u.getId(), u.getUsername(), u.getEmail(), u.getRole());
    }

    // Role-based dashboards
    @GetMapping("/owner/dashboard")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    public ResponseEntity<String> getOwnerDashboard() {
        return ResponseEntity.ok("Owner Dashboard - Welcome to your building permit management system!");
    }

    @GetMapping("/consultant/dashboard")
    @PreAuthorize("hasAnyRole('CONSULTANT', 'ADMIN')")
    public ResponseEntity<String> getConsultantDashboard() {
        return ResponseEntity.ok("Consultant Dashboard - Manage your building permit consultations!");
    }

    @GetMapping("/engineer/dashboard")
    @PreAuthorize("hasAnyRole('ENGINEER', 'ADMIN')")
    public ResponseEntity<String> getEngineerDashboard() {
        return ResponseEntity.ok("Engineer Dashboard - Review and approve building permits!");
    }

    @GetMapping("/government-board/dashboard")
    @PreAuthorize("hasAnyRole('GOVERNMENT_BOARD', 'ADMIN')")
    public ResponseEntity<String> getGovernmentBoardDashboard() {
        return ResponseEntity.ok("Government Board Dashboard - Oversee building permit regulations!");
    }
}
 