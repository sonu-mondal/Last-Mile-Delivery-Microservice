package com.vit.lmd.driverMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DriverMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriverMsApplication.class, args);
	}

}
