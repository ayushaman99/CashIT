package com.fintech.cashit.service;

import com.fintech.cashit.repository.UserRepository;
import com.fintech.cashit.entity.User;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fintech.cashit.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;

    public User saveUser(User user)
    {
        return userRepository.save(user);
    }

}
