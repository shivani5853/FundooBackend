package com.bridgelabz.fundoonotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.fundoonotes.utility.Springmail;

@Configuration
public class SpringMailConfiguration {
	
	@Bean
	public Springmail getSpringmail() {
		return new Springmail();

	}
}
