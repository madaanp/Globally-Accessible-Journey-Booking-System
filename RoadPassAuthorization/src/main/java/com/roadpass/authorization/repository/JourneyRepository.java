package com.roadpass.authorization.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.roadpass.authorization.models.Journey;

import java.util.List;
import java.util.Optional;

public interface JourneyRepository extends MongoRepository<Journey, String> {
    Optional<Journey> findById(String Id);
    List<Journey> findByUsername(String username);
}
