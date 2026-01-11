package com.vit.lmd.stopMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StopMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StopMsApplication.class, args);
	}

}
