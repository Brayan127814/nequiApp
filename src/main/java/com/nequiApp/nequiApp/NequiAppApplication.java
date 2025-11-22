package com.nequiApp.nequiApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class NequiAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NequiAppApplication.class, args);
        System.out.println("http://localhost:8085/");
	}

}
