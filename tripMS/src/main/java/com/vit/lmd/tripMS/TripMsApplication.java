package com.vit.lmd.tripMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class TripMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripMsApplication.class, args);
	}



}
