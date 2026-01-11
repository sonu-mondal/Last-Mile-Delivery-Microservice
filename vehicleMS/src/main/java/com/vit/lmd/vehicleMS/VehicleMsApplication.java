package com.vit.lmd.vehicleMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VehicleMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleMsApplication.class, args);
	}

}
