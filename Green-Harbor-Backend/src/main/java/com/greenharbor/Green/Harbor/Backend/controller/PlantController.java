package com.greenharbor.Green.Harbor.Backend.controller;

import com.greenharbor.Green.Harbor.Backend.model.Plant;
import com.greenharbor.Green.Harbor.Backend.repository.PlantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plants")
public class PlantController {
    @Autowired
    private PlantRepo plantRepo;

    @GetMapping
    public List<Plant> getAllPlants() {
        return plantRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlantById(@PathVariable String id) {
        return ResponseEntity.of(plantRepo.findById(id));
    }

    @GetMapping("/search")
    public List<Plant> searchPlants(@RequestParam("q") String query) {
        return plantRepo.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
