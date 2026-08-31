package com.fintech.cashit.service;

import com.fintech.cashit.DTO.LoginRequest;
import com.fintech.cashit.DTO.UserResponseDTO;
import com.fintech.cashit.entity.Role;
import com.fintech.cashit.entity.User;
import com.fintech.cashit.exception.UserNotFoundException;
import com.fintech.cashit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already registered");
        }

        user.setId(null);
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public User getUserById(Long id, Authentication authentication) {
        User user = findUser(id);
        verifyOwnerOrAdmin(user, authentication);
        return user;
    }

    public User updateUser(
            Long id,
            User updatedUser,
            Authentication authentication) {

        User user = findUser(id);
        verifyOwnerOrAdmin(user, authentication);

        if (updatedUser.getName() != null && !updatedUser.getName().isBlank()) {
            user.setName(updatedUser.getName());
        }

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {
            User existingUser = userRepository.findByEmail(updatedUser.getEmail());

            if (existingUser != null && !existingUser.getId().equals(id)) {
                throw new IllegalArgumentException("Email is already registered");
            }

            user.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id, Authentication authentication) {
        User user = findUser(id);
        verifyOwnerOrAdmin(user, authentication);
        userRepository.delete(user);
    }

    public UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user != null &&
                passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return jwtService.generateToken(user.getEmail());
        }

        return "Invalid Credentials";
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void verifyOwnerOrAdmin(
            User targetUser,
            Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();

        boolean isOwner = currentUser.getId().equals(targetUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You cannot access this user");
        }
    }
}