package com.hermes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AutoConfiguration
public class HermesDataServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HermesDataServerApplication.class, args);
	}

}
