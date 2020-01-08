package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.NoteDto;

public interface NoteServiceInf {

	public boolean create(NoteDto noteDto, String token);

	public boolean delete();

}
