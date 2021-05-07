package com.roadpass.authorization.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.roadpass.authorization.models.Driver;

public interface DriverRepository extends MongoRepository<Driver, String> {
    Driver findByUsername(String username);
}
