package com.ds.backend.serverbackend.repository;

import com.ds.backend.serverbackend.model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverRepository extends MongoRepository<Driver, String> {
    Driver findByUsername(String username);
}
