package com.roadpass.loadbalancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.roadpass.loadbalancer.request.BookRide;
import com.roadpass.loadbalancer.response.RequestResponse;

@CrossOrigin
@RestController
public class ServiceLoadBalancer {

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	private RestTemplate restTemplate = new RestTemplate();
	
	private final String producerService = "producer-service";
	
	@Retryable(value=Exception.class, maxAttempts=1, backoff = @Backoff(delay=100))
	@PostMapping("/bookRide")
	public RequestResponse bookRide(@RequestBody BookRide bookRide) throws Exception{
		try {
			ServiceInstance producerServiceInstance = loadBalancerClient.choose(producerService);	
			System.out.println(producerServiceInstance.getPort());
			String url = producerServiceInstance.getUri().toString() + "/api/producer/bookRide";
			RequestResponse bookRideResponse = restTemplate.postForObject(url, bookRide, RequestResponse.class);
			return bookRideResponse;
		}
		catch(Exception e) {
			throw new Exception(e);
		}
	}

	@PostMapping("/cancelRide")
	public RequestResponse cancelRide(@RequestBody BookRide bookRide) {
		try {			
			ServiceInstance producerServiceInstance = loadBalancerClient.choose(producerService);
			System.out.println(producerServiceInstance.getPort());
			String url = producerServiceInstance.getUri().toString() + "/api/producer/cancelRide";
			RequestResponse bookRideResponse = restTemplate.postForObject(url, bookRide, RequestResponse.class);
			return bookRideResponse;
		}catch(Exception e) {
			RequestResponse response = new RequestResponse();
			response.setCode("500");
			response.setMessage("Request couldn't be processed, please try again");
			return response;
		}
	}
	
	@Recover
	public RequestResponse retryBookRide(@RequestBody BookRide bookRide) {
		try {
			ServiceInstance producerServiceInstance = loadBalancerClient.choose(producerService);	
			System.out.println(producerServiceInstance.getPort());
			String url = producerServiceInstance.getUri().toString() + "/api/producer/bookRide";
			RequestResponse bookRideResponse = restTemplate.postForObject(url, bookRide, RequestResponse.class);
			Thread.sleep(1000);
			return bookRideResponse;
		}
		catch(Exception e) {
			RequestResponse response = new RequestResponse();
			response.setCode("500");
			response.setMessage("Request couldn't be processed, please try again");
			return response;
		}
	}
	
}
