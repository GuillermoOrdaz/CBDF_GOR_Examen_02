package com.upiiz.pyments.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upiiz.pyments.entities.User;
import com.upiiz.pyments.repositories.UserRepository;

@Service
public class UserServices {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
