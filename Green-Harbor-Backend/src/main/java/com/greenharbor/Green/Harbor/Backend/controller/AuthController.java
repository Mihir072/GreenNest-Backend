package com.greenharbor.Green.Harbor.Backend.controller;

import com.greenharbor.Green.Harbor.Backend.config.AuthRequest;
import com.greenharbor.Green.Harbor.Backend.config.JwtUtil;
import com.greenharbor.Green.Harbor.Backend.model.Plant;
import com.greenharbor.Green.Harbor.Backend.model.User;
import com.greenharbor.Green.Harbor.Backend.repository.UserRepo;
import com.greenharbor.Green.Harbor.Backend.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.VariableOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authService;


                                //-------------Register & Login-------------//
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
       try {
           User saved = authService.register(user);
           return ResponseEntity.status(HttpStatus.OK).body(saved);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this email is exist..");
       }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found :("));

        if (!encoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid password");

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
        return ResponseEntity.ok(Map.of("token", token, "role", user.getRole(), "email", user.getEmail()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        // Extract token from header (usually in format: Bearer <token>)
        String token = authHeader.replace("Bearer ", "");

        // For now: just acknowledge the request (you could blacklist the token if needed)
        return ResponseEntity.ok(Map.of("message", "Logout successful. Token discarded on client."));
    }



    //-------------USER CRUD OPERATIONS-------------//

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> showAllUsers(){ return userRepo.findAll();}

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        userRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("Message","User Deleted Successfully :)"));
    }

    @PutMapping("/users")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User updated){
        User user = userRepo.findById(id).orElseThrow();
        user.setName(updated.getName());
        user.setEmail(updated.getEmail());
        user.setPassword(updated.getPassword());
        user.setAddress(updated.getAddress());
        User savedUser = userRepo.save(user);
        return ResponseEntity.ok(Map.of("User Updated Successfully :)", savedUser));
    }



                       //-------------Forgot Password Operation-------------//

    @PutMapping("/passwordForgot/{email}")
    public ResponseEntity<?> passwordForgot(@PathVariable String email, @RequestBody User updated){
        User user = userRepo.findByEmail(email).orElseThrow();
        user.setPassword(encoder.encode(updated.getPassword()));
        User savedUser = userRepo.save(user);
        return ResponseEntity.ok(Map.of("Password Updated Successfully :)", savedUser));
    }


    @GetMapping("/users/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.of(userRepo.findByEmail(email));
    }
}