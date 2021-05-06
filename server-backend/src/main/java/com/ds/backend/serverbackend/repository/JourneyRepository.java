package com.ds.backend.serverbackend.repository;

import com.ds.backend.serverbackend.model.Journey;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface JourneyRepository extends MongoRepository<Journey, String> {
    Optional<Journey> findById(String Id);
    List<Journey> findByUsername(String username);

    List<Journey> findByStartingPoint(String starting_point);
    List<Journey> findByEndingPoint(String ending_point);

}
