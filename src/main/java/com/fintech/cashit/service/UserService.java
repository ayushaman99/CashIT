package com.fintech.cashit.service;

import com.fintech.cashit.DTO.LoginRequest;
import com.fintech.cashit.DTO.UserResponseDTO;
import com.fintech.cashit.repository.UserRepository;
import com.fintech.cashit.entity.User;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.fintech.cashit.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private  UserRepository userRepository;

    public User saveUser(User user)
    {
      user.setPassword(passwordEncoder.encode(user.getPassword()));

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
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }
    public boolean login(LoginRequest request){
        User user=userRepository.findByEmail(request.getEmail());
        return user!=null && passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

}
