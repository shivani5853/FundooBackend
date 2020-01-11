package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Note_Id")
	private Long noteId;

	@Column(name = "Title")
	@NotEmpty(message = "Invalied Title")
	private String title;

	@Column(name = "Description")
	private String description;

	@Column(columnDefinition = "boolean default false")
	private Boolean isPinned;

	@Column(columnDefinition = "boolean default false")
	private Boolean isArchive;

	@Column(columnDefinition = "boolean default false")
	private Boolean isTrash;

	@Column(name = "CreatedAt")
	@DateTimeFormat
	private LocalDateTime createdAt;

	@Column(columnDefinition = "varchar(10) default 'ffffff'")
	private String colour;

	@Column(name = "UpdateTime")
	@DateTimeFormat
	private Date updateTime;

	@Column(name = "reminderTime")
	private LocalDateTime reminderTime;

	@Column(name = "reminder")
	private String reminder;

	@ManyToOne
	@JoinColumn(name = "UserId")
	private User noteUser;

	@Column(columnDefinition = "boolean default false")
	private boolean isVerified;

	public Notes(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public void setupdateTime()
	{
		this.updateTime=new Date();
	}

}
