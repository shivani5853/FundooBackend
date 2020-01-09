package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.FundoonotesApplication;
import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.service.NoteServiceInf;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoteServiceImplementation implements NoteServiceInf {

	//private static final Logger loggger = (Logger) LoggerFactory.getLogger(FundoonotesApplication.class);
	
	@Autowired
	private JwtGenerator jwtGenerator;
	

	
	@Autowired
	private NoteRepository noteRepository; 
	
	@Override
	public Notes save(NoteDto noteDto, String token) {
		
		try {
			log.info("fsdsd");
			Long id=jwtGenerator.parse(token);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Notes delete() {
		return null;
		
	}

}
