package com.bridgelabz.fundoonotes.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Labels {
	
	@Id
	@Column(name = "labelId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long labelId;
	
	@Column(name = "labelName",nullable = false)
	private String labelName;
	
	@ManyToMany
	@JoinColumn(name = "UserId")
	private User labelUser;
	
	@ManyToMany(mappedBy = "lebel")
	private List<Notes> noteList;
}
