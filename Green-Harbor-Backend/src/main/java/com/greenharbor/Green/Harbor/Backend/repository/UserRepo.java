package com.greenharbor.Green.Harbor.Backend.repository;

import com.greenharbor.Green.Harbor.Backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}

