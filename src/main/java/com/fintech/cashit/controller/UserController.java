package com.fintech.cashit.controller;

import com.fintech.cashit.DTO.LoginRequest;
import com.fintech.cashit.DTO.UserResponseDTO;
import com.fintech.cashit.entity.User;
import com.fintech.cashit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("/create")
    public UserResponseDTO createUser(@RequestBody User user) {
        return userService.convertToDTO(userService.saveUser(user));
    }

    @GetMapping("/findallusers")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(
            @PathVariable Long id,
            Authentication authentication) {

        return userService.convertToDTO(
                userService.getUserById(id, authentication)
        );
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser,
            Authentication authentication) {

        return userService.convertToDTO(
                userService.updateUser(id, updatedUser, authentication)
        );
    }

    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Long id,
            Authentication authentication) {

        userService.deleteUser(id, authentication);
        return "User deleted";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}