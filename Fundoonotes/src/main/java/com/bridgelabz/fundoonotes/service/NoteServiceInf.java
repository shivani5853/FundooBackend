package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.model.Notes;

public interface NoteServiceInf {

	public Notes save(NoteDto noteDto, String token);

	public Notes delete();

}
