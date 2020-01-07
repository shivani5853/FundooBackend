package com.bridgelabz.fundoonotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.utility.Utility;

@Configuration
public class ApplicationConfig {

	@Bean
	public User getUser() {
		return new User();

	}

	@Bean
	public Utility getUtility() {
		return new Utility();
	}

	@Bean
	public UpdatePassword getUpdatePassword() {
		return new UpdatePassword();
	}
}
