package com.roadpass.authorization.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roadpass.authorization.models.Driver;
import com.roadpass.authorization.repository.DriverRepository;
import com.roadpass.authorization.request.LoginRequest;
import com.roadpass.authorization.request.RegisterRequest;
import com.roadpass.authorization.response.ApiResponse;
import com.roadpass.authorization.utility.JBSUtility;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
    @Autowired
    DriverRepository driverRepository;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
            Driver existingDriver = driverRepository.findByUsername(loginRequest.getUsername());
            if(existingDriver!=null){
                if(existingDriver.getPassword().equals(loginRequest.getPassword())){
                    return ResponseEntity.ok(new ApiResponse(0, JBSUtility.DRIVER_LOGIN_SUCCESS, existingDriver.getUsername(), existingDriver.getEmail(), existingDriver.getCountry(), existingDriver.getCountryCode()));
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

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {		
		try {
            Driver existingDriver = driverRepository.findByUsername(signUpRequest.getUsername());
            if(existingDriver!=null){
                return ResponseEntity.ok(new ApiResponse(1, JBSUtility.CREATE_DRIVER_FAILURE_EXISTS));
            }
            else{
                Driver newDriver = driverRepository.save(new Driver(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getCountry(), signUpRequest.getCountryCode()));
                return ResponseEntity.ok(new ApiResponse(0, JBSUtility.CREATE_DRIVER_SUCCESS));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(-1, JBSUtility.JBS_SERVER_ERROR));
        }	
	}
}
