package com.greenharbor.Green.Harbor.Backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "plants")
public class Plant {

    @Id
    private String id;
    private String name;
    private String description;
    private int price;
    private String category;
    private String imageUrl;
    private int stock;
    private Date createdAt = new Date();
}
