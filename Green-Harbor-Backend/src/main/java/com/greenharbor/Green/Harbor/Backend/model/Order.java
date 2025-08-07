package com.greenharbor.Green.Harbor.Backend.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String userId;
    private String name;
    private String address;
    private List<OrderItem> items;
    private int totalAmount;
    private String status = "Placed";
    private Date createdAt = new Date();
    private String email;
}
