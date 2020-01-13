package com.bridgelabz.fundoonotes.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.responses.Responses;
import com.bridgelabz.fundoonotes.service.LabelServiceInf;

@RestController
@RequestMapping("/label")
public class LabelController {

	@Autowired
	private LabelServiceInf labelServiceInf;

	@PostMapping("/create")
	public ResponseEntity<Responses> createLabel(@RequestBody LabelDto label, @PathVariable("token") String token) {
		Labels result = labelServiceInf.create(label);

		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Responses("Label created Sucessfully!!!", 200));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Responses("Something went wrong!!!", 400));
		}
	}
}
