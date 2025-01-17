package com.cvmaker.cvmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cvmaker.cvmaker")
public class CvmakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CvmakerApplication.class, args);
	}

}
