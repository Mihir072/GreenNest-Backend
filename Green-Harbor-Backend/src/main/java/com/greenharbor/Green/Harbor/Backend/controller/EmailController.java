package com.greenharbor.Green.Harbor.Backend.controller;

import com.greenharbor.Green.Harbor.Backend.config.EmailUtil;
import com.greenharbor.Green.Harbor.Backend.model.EmailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class EmailController {
    @PostMapping("/send-confirmation-email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest req) {
        try {
            EmailUtil.sendEmail(req.getToEmail(), req.getSubject(), req.getMessage());
            return ResponseEntity.ok("Email sent successfully");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
        }
    }

}
