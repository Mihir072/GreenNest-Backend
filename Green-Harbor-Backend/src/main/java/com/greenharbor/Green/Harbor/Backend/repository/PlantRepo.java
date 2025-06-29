package com.greenharbor.Green.Harbor.Backend.repository;

import com.greenharbor.Green.Harbor.Backend.model.Plant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlantRepo extends MongoRepository<Plant, String> {}
