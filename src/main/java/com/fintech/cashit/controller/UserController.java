package com.fintech.cashit.controller;

import com.fintech.cashit.DTO.LoginRequest;
import com.fintech.cashit.DTO.UserResponseDTO;
import com.fintech.cashit.entity.User;
import com.fintech.cashit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "its working";
    }
    @PostMapping("/users")
    public UserResponseDTO createUser(@RequestBody User user)
    {
       User savedUser=userService.saveUser(user);
       return userService.convertToDTO(savedUser);
    }
    @GetMapping("/findallusers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/users/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return userService.convertToDTO(user);
    }
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted";
    }
    @PostMapping("/login")
    public boolean login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
