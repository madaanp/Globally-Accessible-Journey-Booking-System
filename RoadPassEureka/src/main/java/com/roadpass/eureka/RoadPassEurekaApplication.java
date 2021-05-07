package com.roadpass.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RoadPassEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoadPassEurekaApplication.class, args);
	}

}
