package com.greenharbor.Green.Harbor.Backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private String plantId;
    private String name;
    private int price;
    private int quantity;
}