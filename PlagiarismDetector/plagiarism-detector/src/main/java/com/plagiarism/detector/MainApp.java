package com.plagiarism.detector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * MainApp to run spring web application
 * 
 * @author Monica
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
public class MainApp extends SpringBootServletInitializer {
	private static Logger logging = LogManager.getLogger(MainApp.class);

	/**
	 * Main spring web app run
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		logging.info("Starting Spring Boot application..");
		SpringApplication.run(MainApp.class, args);
	}

}