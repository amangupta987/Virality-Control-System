package com.virality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ViralityControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViralityControlApplication.class, args);
	}

}
