package com.bridgelabz.fundoonotes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
public class Collaborator {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;

	@NonNull
	private String email;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "noteID")
	private Notes note ;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Notes getNote() {
		return note;
	}

	public void setNote(Notes note) {
		this.note = note;
	}

	public Collaborator(long id, @NonNull String email, Notes note) {
		super();
		Id = id;
		this.email = email;
		this.note = note;
	}

	public Collaborator() {
		super();
	}

	@Override
	public String toString() {
		return "Collaborator [Id=" + Id + ", email=" + email + ", note=" + note + "]";
	}
	
}