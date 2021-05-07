package com.roadpass.loadbalancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roadpass.loadbalancer.request.LoginRequest;
import com.roadpass.loadbalancer.request.RegisterRequest;
import com.roadpass.loadbalancer.response.ApiResponse;
import com.roadpass.loadbalancer.utility.JBSUtility;

@CrossOrigin
@RestController
public class AuthLoadBalancer {
	
	@Autowired
	private LoadBalancerClient loadBalancerClient;

	private RestTemplate restTemplate = new RestTemplate();
	
	private final String mainService = "main-service";
	
	@PostMapping("/register")
	public ResponseEntity<?> authenticateUser(@RequestBody RegisterRequest registerRequest) {
		try {			
			ServiceInstance authorizationInstance = loadBalancerClient.choose(mainService);
			System.out.println(authorizationInstance.getPort());
			String url = authorizationInstance.getUri().toString() + "/api/auth/signup";
			Object registerResponse = restTemplate.postForObject(url, registerRequest, Object.class);
			ObjectMapper objectMapper = new ObjectMapper();
			ApiResponse apiResponse = objectMapper.convertValue(registerResponse, ApiResponse.class);
			return ResponseEntity.ok(apiResponse);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(-1, JBSUtility.JBS_SERVER_ERROR));
		}
	}
	
    @Retryable(value=Exception.class, maxAttempts=1, backoff = @Backoff(delay=100))
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest signinRequest) throws Exception{
		ApiResponse apiResponse = new ApiResponse();
		try {			
			ServiceInstance authorizationInstance = loadBalancerClient.choose(mainService);
			System.out.println(authorizationInstance.getPort());
			String url = authorizationInstance.getUri().toString() + "/api/auth/signin";
			Object loginResponse = restTemplate.postForObject(url, signinRequest, Object.class);
			ObjectMapper objectMapper = new ObjectMapper();
			apiResponse = objectMapper.convertValue(loginResponse, ApiResponse.class);	
			return ResponseEntity.ok(apiResponse);
		}catch(Exception e) {
			throw new Exception(e);
		}
	}
	
    @Recover
    public ResponseEntity<?> retryAuthentication(@RequestBody LoginRequest signinRequest){
		ApiResponse apiResponse = new ApiResponse();
		try {			
			ServiceInstance authorizationInstance = loadBalancerClient.choose(mainService);
			System.out.println(authorizationInstance.getPort());
			String url = authorizationInstance.getUri().toString() + "/api/auth/signin";
			Object loginResponse = restTemplate.postForObject(url, signinRequest, Object.class);
			Thread.sleep(1000);
			ObjectMapper objectMapper = new ObjectMapper();
			apiResponse = objectMapper.convertValue(loginResponse, ApiResponse.class);	
			return ResponseEntity.ok(apiResponse);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(-1, JBSUtility.JBS_SERVER_ERROR));
		}
	}
}
