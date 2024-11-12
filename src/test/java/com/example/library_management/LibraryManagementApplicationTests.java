package com.example.library_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class LibraryManagementApplicationTests {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplicationTests.class, args);
	}

}
