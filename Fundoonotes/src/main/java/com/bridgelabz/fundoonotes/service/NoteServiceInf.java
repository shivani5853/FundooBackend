package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.ReminderDto;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.User;

public interface NoteServiceInf {

	public Notes save(NoteDto noteDto, String token);

	public Integer delete(long noteId, String token);

	public Integer pinned(long noteId, String token);

	public Integer archive(long noteId, String token);

	public Notes colour(long noteId, String token, String colour);

	public Notes reminder(ReminderDto reminder, long noteId, String token);

	public Notes deleteParmanet(long noteId, String token);

	public List<Notes> searchByUserId(long userId, String token);

	public List<Notes> searchByNoteId(long noteId, String token);

	public Notes verify(String token);

	public List<Notes> getAllNoteByPage(Integer pageNo, Integer pageSize);
}
