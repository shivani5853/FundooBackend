package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.ReminderDto;
import com.bridgelabz.fundoonotes.model.Notes;

public interface NoteServiceInf {

	public Notes save(NoteDto noteDto, String token);

	public Integer delete(long noteId, String token);

	public Integer pinned(long noteId, String token);

	public Integer archive(long noteId, String token);

	public Notes colour(long noteId, String token, String colour);

	public Notes reminder(ReminderDto reminder, long noteId, String token);

	public Notes deleteParmanet(long noteId, String token);

}
