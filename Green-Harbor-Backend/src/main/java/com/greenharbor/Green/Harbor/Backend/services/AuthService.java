package com.greenharbor.Green.Harbor.Backend.services;

import com.greenharbor.Green.Harbor.Backend.config.AppConstantConfig;
import com.greenharbor.Green.Harbor.Backend.config.AuthRequest;
import com.greenharbor.Green.Harbor.Backend.config.JwtUtil;
import com.greenharbor.Green.Harbor.Backend.model.User;
import com.greenharbor.Green.Harbor.Backend.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public User register(User user){
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()){
            throw new IllegalArgumentException();

        }

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public Map<String, Object> login(AuthRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException(AppConstantConfig.USER_NOT_FOUND));

        if (!encoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException(AppConstantConfig.INVALID_PASSWORD);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
        String redisKey = AppConstantConfig.SESSION + user.getId();
        redisTemplate.opsForValue().set(redisKey, token);

        return Map.of(
                AppConstantConfig.TOKEN, token,
                AppConstantConfig.ROLE, user.getRole(),
                AppConstantConfig.EMAIL, user.getEmail()
        );
    }


    public Map<String, String> logout(String authHeader){
        String token = authHeader.replace(AppConstantConfig.BEARER, "");
        return Map.of(AppConstantConfig.MESSAGE, AppConstantConfig.LOGOUT_SUCCESSFUL);
    }
}
