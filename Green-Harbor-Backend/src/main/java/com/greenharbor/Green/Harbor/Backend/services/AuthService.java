package com.greenharbor.Green.Harbor.Backend.services;

import com.greenharbor.Green.Harbor.Backend.model.User;
import com.greenharbor.Green.Harbor.Backend.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    public User register(User user){
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()){
            throw new IllegalArgumentException("User with this email already exists");

        }

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
