package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.responses.Responses;
import com.bridgelabz.fundoonotes.service.NoteServiceInf;

@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteServiceInf noteServiceInf;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	public ResponseEntity<Responses> createNote() {

	}

}
