package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	@NotNull
	@Size(min = 2, max = 50, message = "Invalied First Name")
	private String firstName;

	@NotNull
	@Size(min = 2, max = 50, message = "Invalied Last Name")
	private String lastName;

	@NotNull
	@Email
	private String email;

	private String password;

	@Size(max = 10, message = "Invalied Phone Number")
	private long phoneNumber;
}
