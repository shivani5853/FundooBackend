package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.model.Notes;

public interface ElasticsearchService {
	public String createNote(Notes note);

	public void updateNote(Long noteId);

	public String deleteNote(Long noteId);

	public List<Notes> searchNote(String title);

}
