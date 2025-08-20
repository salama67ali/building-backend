package com.computing.BuildingsPermitted.service;

import com.computing.BuildingsPermitted.dto.AuthResponse;
import com.computing.BuildingsPermitted.dto.UserRegistrationRequest;
import com.computing.BuildingsPermitted.model.User;
import com.computing.BuildingsPermitted.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider; // concrete class, not generic

    /** Register a new user */
    public AuthResponse register(UserRegistrationRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Confirm password check (if confirmPassword is null, treat as matching to support internal admin addUser path)
        String confirm = request.getConfirmPassword();
        if (confirm != null && !confirm.equals(request.getPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Normalize role: default OWNER if missing, uppercase and replace '-' with '_'
        String role = request.getRole();
        if (role == null || role.isBlank()) {
            role = "OWNER";
        } else {
            role = role.trim().toUpperCase().replace('-', '_');
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(role);

        User savedUser = userRepository.save(user);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole(),
                "User registered successfully"
        );
    }

    /** Authenticate user by email/password/role and return JWT */
    public AuthResponse login(String email, String password, String role) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Optional<User> userOpt = userRepository.findByEmail(email);
        User user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));

        // Validate provided role matches stored user role
        if (role == null || user.getRole() == null || !user.getRole().equalsIgnoreCase(role.trim())) {
            throw new RuntimeException("Invalid role for this account");
        }

        String token = jwtTokenProvider.generateToken(authentication, user.getRole());

        return new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }

    /** Get current logged-in user */
    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
