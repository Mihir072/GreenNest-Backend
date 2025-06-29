package com.greenharbor.Green.Harbor.Backend.controller;

import com.greenharbor.Green.Harbor.Backend.model.Plant;
import com.greenharbor.Green.Harbor.Backend.repository.PlantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/plants")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPlantController {
    @Autowired
    private PlantRepo repo;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Plant plant) {
        return ResponseEntity.ok(repo.save(plant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Plant updated) {
        Plant plant = repo.findById(id).orElseThrow();
        plant.setName(updated.getName());
        plant.setDescription(updated.getDescription());
        plant.setPrice(updated.getPrice());
        plant.setStock(updated.getStock());
        plant.setImageUrl(updated.getImageUrl());
        plant.setCategory(updated.getCategory());
        return ResponseEntity.ok(repo.save(plant));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repo.deleteById(id);
    }

    @GetMapping
    public List<Plant> getAll() {
        return repo.findAll();
    }
}
