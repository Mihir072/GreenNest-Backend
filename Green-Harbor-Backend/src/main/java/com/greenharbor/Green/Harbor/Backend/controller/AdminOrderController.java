package com.greenharbor.Green.Harbor.Backend.controller;

import com.greenharbor.Green.Harbor.Backend.model.Order;
import com.greenharbor.Green.Harbor.Backend.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {
    @Autowired
    private OrderRepo orderRepo;

    @GetMapping
    public List<Order> allOrders() {
        return orderRepo.findAll();
    }

    @DeleteMapping
    public void deleteOder(@PathVariable String id){orderRepo.deleteById(id);}
}
