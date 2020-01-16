package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.NoteDto;
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

		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Responses("Label created Sucessfully!!!", 200));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Responses("Something went wrong!!!", 400));
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Responses> deleteLabel(@RequestBody LabelDto label, @RequestHeader("token") String token) {
		Labels result = labelServiceInf.deleteLabel(label, token);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Responses("Label deleted Sucessfully!!!", 200));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Responses("Something went wrong!!!", 400));
		}
	}

	@PostMapping("/mapToNote/{noteId}")
	public ResponseEntity<Responses> labelMapToNote(@RequestBody LabelDto label,@RequestHeader("token") String token,@RequestHeader Long noteId)
	{
		Labels result = labelServiceInf.labelMapToNote(label, token, noteId);
		if(result!=null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Responses("Label Sucessfully Map with note!!!", 200));
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Responses("Something went wrong !!!",400));
		}
	}
	
}