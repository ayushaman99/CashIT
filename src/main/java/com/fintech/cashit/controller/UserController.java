package com.fintech.cashit.controller;

import com.fintech.cashit.entity.User;
import com.fintech.cashit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "its working";
    }
    @PostMapping("/users")
    public User createUser(@RequestBody User user)
    {
        return userService.saveUser(user);
    }
}
