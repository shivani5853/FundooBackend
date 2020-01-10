package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.ReminderDto;
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
	public ResponseEntity<Responses> createNote(@RequestBody NoteDto noteDto, @RequestHeader("token") String token) {

		Notes result = noteServiceInf.save(noteDto, token);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Responses("Registration Successfully", 200, noteDto));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Responses("User Already Exist", 400, noteDto));
		}
	}

	@DeleteMapping("/delete/{noteId}")
	public ResponseEntity<Responses> deleteNote(@PathVariable("noteId") Long noteId, @RequestHeader String token) {
		Notes result = noteServiceInf.delete(noteId, token);
		return null;
	}

	@PutMapping("/pinned/{noteId}")
	public ResponseEntity<Responses> pinnedNote(@RequestHeader("token") String token,
			@PathVariable("noteId") Long note_id) {
		System.out.println(token);
		Integer result = noteServiceInf.pinned(note_id, token);
		System.out.println(result);
		if (result == 1) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Responses("Sucessfully Unpinned", 200));
		} else if (result == 0) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Responses("Sucessfully Pinned", 200));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Responses("Sorry Something went wrong!!!", 400));
		}
	}

	@PutMapping("/archive/{noteId}")
	public ResponseEntity<Responses> archiveNote(@RequestHeader("token") String token,
			@PathVariable("noteId") Long note_id) {
		System.out.println(token);
		Integer result = noteServiceInf.pinned(note_id, token);
		System.out.println(result);
		if (result == 1) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Responses("Sucessfully UnArchive", 200));
		} else if (result == 0) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Responses("Sucessfully Archive", 200));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Responses("Sorry Something went wrong!!!", 400));
		}
	}

	@PutMapping("/colour/{noteId}")
	public ResponseEntity<Responses> colourNote(@RequestHeader("token") String token,
			@PathVariable("noteId") Long note_id, @RequestParam("colour") String colour) {
		Notes result = noteServiceInf.colour(note_id, token, colour);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Responses("Colour Change Successfully", 200));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Responses("Something went wrong!!!", 400));
		}
	}

	@PostMapping("/reminder/{noteId}")
	public ResponseEntity<Responses> reminderMe(@RequestBody ReminderDto reminder, @RequestHeader String token,
			@PathVariable("noteId") Long noteId) {
		Notes result = noteServiceInf.remind(reminder, noteId, token);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Responses("Reminder set sucessfully", 200));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Responses("Something went wrong!!!", 400));
		}

	}
}
