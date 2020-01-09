package com.bridgelabz.fundoonotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.service.NoteServiceInf;


@Configuration
public class ApplicationConfig {

	@Bean 
	public BCryptPasswordEncoder getBcrBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
