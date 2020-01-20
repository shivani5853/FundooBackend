package com.bridgelabz.fundoonotes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class ProfilePic {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	private String profilePicName;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId")
	private User userLabel;

	public ProfilePic(String profilePicName, User userLabel) {
		super();
		this.profilePicName = profilePicName;
		this.userLabel = userLabel;
	}
	
}
