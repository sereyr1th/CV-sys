package com.cvmaker.cvmaker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cvmaker.cvmaker.dto.UserDto;
import com.cvmaker.cvmaker.entity.User;
import com.cvmaker.cvmaker.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserDto userDto) {
        // Check if the email already exists
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        // Create and save new user
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }
}
