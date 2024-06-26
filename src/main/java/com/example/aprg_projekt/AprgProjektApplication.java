package com.example.aprg_projekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
public class AprgProjektApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AprgProjektApplication.class, args);
	}

}
