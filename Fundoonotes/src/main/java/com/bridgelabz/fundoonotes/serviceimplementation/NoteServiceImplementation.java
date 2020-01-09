package com.bridgelabz.fundoonotes.serviceimplementation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.NoteServiceInf;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoteServiceImplementation implements NoteServiceInf {

	@Autowired
	private JwtGenerator jwtGenerator;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Notes save(NoteDto noteDto, String token) {

		try {
			long id = jwtGenerator.parse(token);
			System.out.println(token);
			log.info("Id:" + id + " Note Description:" + noteDto.getDescription());

			User user = userRepository.findById(id);
			if (user != null) {
				Notes note = new Notes(noteDto.getTitle(), noteDto.getDescription());
				note.setNoteUser(user);
				note.setCreatedAt(LocalDateTime.now());
				note.setIsArchive(false);
				note.setIsPinned(false);
				note.setIsTrash(false);
				note.setColour("white");
				System.out.println(note);
				noteRepository.insertData(note.getNoteId(), note.getTitle(), note.getDescription(), note.getCreatedAt(),
						note.getUpdateTime());
				System.out.println(note);
				
				return note;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Notes delete() {
		return null;

	}

}
