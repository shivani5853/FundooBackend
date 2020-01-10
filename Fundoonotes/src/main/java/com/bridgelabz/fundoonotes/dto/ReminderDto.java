package com.bridgelabz.fundoonotes.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReminderDto {
	private String reminderStatus;
	private Date reminder; 
}
