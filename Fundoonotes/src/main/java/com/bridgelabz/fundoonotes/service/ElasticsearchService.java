package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.model.Notes;

public interface ElasticsearchService {
	public String createNote(Notes note);

	public void updateNote(long noteId);

	public String deleteNote(long noteId);

	public List<Notes> searchTitle(String title);

	List<Notes> searchNote(String title, long noteId);

}