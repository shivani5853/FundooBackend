package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.responses.Responses;
import com.bridgelabz.fundoonotes.service.NoteServiceInf;

@RestController
@RequestMapping("/note")
public class NoteController {




	@PostMapping("/create")
	public ResponseEntity<Responses> createNote(@RequestBody NoteDto noteDto,@RequestHeader ("token") String token) {
		//Notes result=noteServiceInf.save(noteDto, token);
		return null;
	}

}
