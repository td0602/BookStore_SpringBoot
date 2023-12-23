package com.example.ebookapp;

import com.example.ebookapp.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EbookAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbookAppApplication.class, args);
	}

}
