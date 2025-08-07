package com.greenharbor.Green.Harbor.Backend.controller;

import com.greenharbor.Green.Harbor.Backend.config.JwtUtil;
import com.greenharbor.Green.Harbor.Backend.model.Order;
import com.greenharbor.Green.Harbor.Backend.model.OrderItem;
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
    public ResponseEntity<?> placeOrder(@RequestBody Order order, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Claims claims = JwtUtil.extractAllClaims(token);
        order.setUserId(claims.get("userId", String.class));

        // Build a clean email body
        String emailBody =
                "Hello " + order.getName() + ",\n\n" +
                        "Thank you for your order! 🎉\n\n" +
                        "Here are your order details:\n\n" +
                        "Items:\n" +
                        formatOrderItems(order.getItems()) + "\n" +
                        "Total Amount: ₹" + order.getTotalAmount() + "\n\n" +
                        "We will deliver your order to:\n" +
                        order.getAddress() + "\n\n" +
                        "Thanks,\nTeam GreenNest";

        emailService.sendEmail(order.getEmail(), "Your Order Confirmation - GreenNest", emailBody);

        return ResponseEntity.ok(orderRepo.save(order));
    }

    private String formatOrderItems(List<OrderItem> items) {
        StringBuilder builder = new StringBuilder();
        for (OrderItem item : items) {
            builder.append("- ")
                    .append(item.getName())
                    .append(" (x")
                    .append(item.getQuantity())
                    .append(") - ₹")
                    .append(item.getPrice())
                    .append("\n");
        }
        return builder.toString();
    }


    @GetMapping("/my-orders")
    public List<Order> userOrders(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Claims claims = jwtUtil.extractAllClaims(token);
        String userId = claims.get("userId", String.class);
        return orderRepo.findByUserId(userId);
    }
}
