package com.greenharbor.Green.Harbor.Backend.repository;

import com.greenharbor.Green.Harbor.Backend.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepo extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
}