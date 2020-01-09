package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.responses.Responses;
import com.bridgelabz.fundoonotes.service.NoteServiceInf;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	private NoteServiceInf noteServiceInf; 


	@PostMapping("/create")
	public ResponseEntity<Responses> createNote(@RequestBody NoteDto noteDto,@RequestHeader ("token") String token) {
		
		Notes result=noteServiceInf.save(noteDto, token);
		System.out.println("result"+result);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Responses("Registration Successfully", 200, noteDto));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Responses("User Already Exist", 400, noteDto));
		}
	}
	
	@DeleteMapping("/delete/{noteId}")
	public ResponseEntity<Responses> deleteNote(@PathVariable("noteId") Long noteId,@RequestBody String token)
	{
		
		return null;
		
	}
	

}
