package com.fintech.cashit.service;

import com.fintech.cashit.repository.UserRepository;
import com.fintech.cashit.entity.User;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fintech.cashit.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;

    public User saveUser(User user)
    {
        return userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public User updateUser(Long id,User updatedUser) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        }
        return userRepository.save(user);
    }

}
