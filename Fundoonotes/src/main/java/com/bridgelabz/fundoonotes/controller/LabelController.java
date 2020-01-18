package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.response.Responses;
import com.bridgelabz.fundoonotes.service.LabelServiceInf;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/label")
public class LabelController {

	@Autowired
	private LabelServiceInf labelServiceInf;

	@PostMapping("/create")
	public ResponseEntity<Responses> createLabel(@RequestBody LabelDto label, @RequestHeader("token") String token) {
		Labels result = labelServiceInf.create(label, token);

		return result != null
				? ResponseEntity.status(HttpStatus.OK).body(new Responses("Label created Sucessfully!!!", 200))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Responses("Something went wrong!!!", 400));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Responses> deleteLabel(@RequestBody LabelDto label, @RequestHeader("token") String token) {
		Labels result = labelServiceInf.deleteLabel(label, token);
		return result != null
				? ResponseEntity.status(HttpStatus.OK).body(new Responses("Label is deleted Sucessfully!!!", 200))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Responses("Something went wrong!!!", 400));
	}

	@PostMapping("/mapToNote/{noteId}")
	public ResponseEntity<Responses> labelMapToNote(@RequestBody LabelDto label, @RequestHeader("token") String token,
			@RequestHeader Long noteId) {
		Labels result = labelServiceInf.labelMapToNote(label, token, noteId);
		return result != null ? ResponseEntity.status(HttpStatus.OK).body(new Responses("Label is map with note", 200))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Responses("Something went wrong", 400));
	}

	@PutMapping("/update")
	public ResponseEntity<Responses> updateLabel(@RequestHeader("token") String token, @RequestHeader long NoteId,
			@RequestHeader long LabelId) {
		Labels result = labelServiceInf.updateLabel(token, NoteId, LabelId);
		return result != null
				? ResponseEntity.status(HttpStatus.OK).body(new Responses("Label update Sucessfully!!!", 200))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Responses("Something went wrong!!!", 400));
	}

	@PostMapping("/addLabel")
	public ResponseEntity<Responses> addLabel(@RequestHeader("token") String token, @RequestHeader Long noteId,
			@RequestHeader Long labelId) {
		Labels result = labelServiceInf.addLabel(token, noteId, labelId);
		return result != null
				? ResponseEntity.status(HttpStatus.OK).body(new Responses("Label is added Sucessfully", 200))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Responses("Something went wrong!!!", 400));
	}

	@GetMapping("/getAllLabel")
	public ResponseEntity<Responses> getAllLabels(@RequestHeader("token") String token) {
		Labels result = labelServiceInf.getAllLabels(token);
		return result != null ? ResponseEntity.status(HttpStatus.OK).body(new Responses("All lebels are", 200))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Responses("Something went wrong!!!", 400));
	}

}