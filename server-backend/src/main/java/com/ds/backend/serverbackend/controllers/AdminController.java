package com.ds.backend.serverbackend.controllers;

import com.ds.backend.serverbackend.model.Driver;
import com.ds.backend.serverbackend.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    DriverRepository driverRepository;

    @PostMapping("/createDriver")
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        try {
            Driver newDriver = driverRepository.save(new Driver(driver.getUsername(), driver.getPassword()));
            return new ResponseEntity<>(newDriver, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/loginDriver")
    public ResponseEntity<Driver> loginDriver(@RequestBody Driver driver) {
        try {
            Driver existingDriver = driverRepository.findByUsername(driver.getUsername());
            if(existingDriver!=null){
                if(existingDriver.getPassword().equals(driver.getPassword())){
                    return new ResponseEntity<>(HttpStatus.OK);
                }else {
                    return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                }
            }else{
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
