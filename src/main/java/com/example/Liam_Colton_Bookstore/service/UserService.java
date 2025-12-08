package com.example.Liam_Colton_Bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Liam_Colton_Bookstore.model.User;
import com.example.Liam_Colton_Bookstore.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private final UserRepo userRepository;

    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
