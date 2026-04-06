package com.screenprog.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/**
 * This is the main class for the application
 * @author Asim Ansari
 * @since November 2024
 * */
@SpringBootApplication
@ConfigurationPropertiesScan("com.screenprog.application.config")
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);



	}

}
