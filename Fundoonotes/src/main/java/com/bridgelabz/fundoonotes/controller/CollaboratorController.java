package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.CollaboratorDto;
import com.bridgelabz.fundoonotes.response.Responses;
import com.bridgelabz.fundoonotes.service.CollaboratorServiceInf;

@RestController
@RequestMapping("/collaborator")
public class CollaboratorController {

	@Autowired
	private CollaboratorServiceInf collaboratorServiceInf;

	@PostMapping("/addCollaborator/{userId}")
	private ResponseEntity<Responses> addCollaborator(@RequestBody CollaboratorDto collaboratorDto,
			@PathVariable("userId") Long userId, @RequestHeader("token") String token) {
		CollaboratorDto result=collaboratorServiceInf.addCollaborator(collaboratorDto,userId,token);
		
		return null;
	}
}
