package com.greenharbor.Green.Harbor.Backend.controller;

import com.greenharbor.Green.Harbor.Backend.config.JwtUtil;
import com.greenharbor.Green.Harbor.Backend.model.Order;
import com.greenharbor.Green.Harbor.Backend.repository.OrderRepo;

import com.greenharbor.Green.Harbor.Backend.services.EmailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@PreAuthorize("hasRole('USER')")
public class OrderController {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody Order order,
                                        @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        Claims claims = JwtUtil.extractAllClaims(token);
        order.setUserId(claims.get("userId", String.class));
        emailService.sendEmail("mihirshigvan716@gmail.com", "Hello "+order.getName(), " Your order for"+order.getItems()+"is placed");
        return ResponseEntity.ok(orderRepo.save(order));
    }

    @GetMapping("/my-orders")
    public List<Order> userOrders(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Claims claims = jwtUtil.extractAllClaims(token);
        String userId = claims.get("userId", String.class);
        return orderRepo.findByUserId(userId);
    }
}
