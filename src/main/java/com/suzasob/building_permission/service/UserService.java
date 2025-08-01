package com.suzasob.building_permission.service;

import java.util.List;
import java.util.Optional;

import com.suzasob.building_permission.dto.AuthRequestDTO;
import com.suzasob.building_permission.dto.RegisterRequestDTO;
import com.suzasob.building_permission.model.User;
import com.suzasob.building_permission.model.UserRole;
import com.suzasob.building_permission.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. Register new user from DTO
    public User registerUser(RegisterRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // hash password
        user.setRole(UserRole.valueOf(dto.getRole().toUpperCase()));
        return userRepository.save(user);
    }

    // 2. Login (validate credentials)
    public Optional<User> authenticateUser(AuthRequestDTO dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                return Optional.of(user); // ✅ correct credentials
            }
        }
        return Optional.empty(); // ❌ invalid login
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
public User registerUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
}
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
