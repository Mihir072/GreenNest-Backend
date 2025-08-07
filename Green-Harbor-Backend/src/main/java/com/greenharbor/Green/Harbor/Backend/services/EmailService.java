package com.greenharbor.Green.Harbor.Backend.services;

import com.greenharbor.Green.Harbor.Backend.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String subject, String body){
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            if (toEmail == null) {
                log.error("Email is null..");
            } else {
                email.setTo(toEmail);
            }
            email.setSubject(subject);
            email.setText(body);
            javaMailSender.send(email);
        } catch (Exception e) {
            log.error("Error coming to send Email..");
        }
    }
}
