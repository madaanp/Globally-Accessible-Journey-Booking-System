package com.ds.backend.serverbackend.controllers;

import com.ds.backend.serverbackend.model.Driver;
import com.ds.backend.serverbackend.repository.DriverRepository;
import com.ds.backend.serverbackend.response.ApiResponse;
import com.ds.backend.serverbackend.utility.JBSUtility;
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
    public ResponseEntity<ApiResponse> createDriver(@RequestBody Driver driver) {
        try {
            Driver existingDriver = driverRepository.findByUsername(driver.getUsername());
            if(existingDriver!=null){
                return ResponseEntity.ok(new ApiResponse(1, JBSUtility.CREATE_DRIVER_FAILURE_EXISTS));
            }
            else{
                Driver newDriver = driverRepository.save(new Driver(driver.getUsername(), driver.getPassword()));
                return ResponseEntity.ok(new ApiResponse(0, JBSUtility.CREATE_DRIVER_SUCCESS));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(-1, JBSUtility.JBS_SERVER_ERROR));
        }
    }

    @PostMapping("/loginDriver")
    public ResponseEntity<ApiResponse> loginDriver(@RequestBody Driver driver) {
        try {
            Driver existingDriver = driverRepository.findByUsername(driver.getUsername());
            if(existingDriver!=null){
                if(existingDriver.getPassword().equals(driver.getPassword())){
                    return ResponseEntity.ok(new ApiResponse(0, JBSUtility.DRIVER_LOGIN_SUCCESS));
                }else {
                    return ResponseEntity.ok(new ApiResponse(1, JBSUtility.DRIVER_LOGIN_FAILURE_PWD));
                }
            }else{
                return ResponseEntity.ok(new ApiResponse(1, JBSUtility.DRIVER_LOGIN_FAILURE_UNAME));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(-1, JBSUtility.JBS_SERVER_ERROR));
        }
    }
}
