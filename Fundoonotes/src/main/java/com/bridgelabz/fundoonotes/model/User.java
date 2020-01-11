package com.bridgelabz.fundoonotes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "User")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "firstName", nullable = false)
	@NotEmpty(message = "Invalied FirstName")
	private String firstName;

	@Column(name = "lastName", nullable = false)
	@NotEmpty(message = "Invalied LastName")
	private String lastName;

	@Email
	@Column(name = "email", nullable = false, unique = true)
	@NotEmpty(message = "Invalied Email Id")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "phoneNumber")
	private Long phoneNumber;

	@Column(columnDefinition = "boolean default false")
	private boolean isVerified;

	@Column(name = "createdAt")
	private Date createdAt;

}
