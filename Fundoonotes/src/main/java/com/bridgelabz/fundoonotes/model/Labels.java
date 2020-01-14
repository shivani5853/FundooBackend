package com.bridgelabz.fundoonotes.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "Label")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Data
public class Labels {

	@Id
	@Column(name = "label_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long labelId;

	@Column(name = "labelName", nullable = false)
	private String labelName;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User labelUser;

	@JsonIgnore
	@ManyToMany(mappedBy = "labels")
	private List<Notes> noteList;

}
