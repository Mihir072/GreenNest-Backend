package com.greenharbor.Green.Harbor.Backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String role; // USER or ADMIN
    private Date createdAt = new Date();
}

