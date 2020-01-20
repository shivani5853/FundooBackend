package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.CollaboratorDto;
import com.bridgelabz.fundoonotes.model.Collaborator;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.CollaboratorRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.CollaboratorServiceInf;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class CollaboratorServiceImplementation implements CollaboratorServiceInf {

	@Autowired
	private JwtGenerator jwtGenerator;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private CollaboratorRepository collaboratorRepository;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public Collaborator addCollaborator(CollaboratorDto collaboratorDto, String token, long noteId) {
		try {
			Collaborator collaborator = new Collaborator();
			Notes note = noteRepository.findById(noteId);
			Collaborator collaboratorDB = collaboratorRepository.findOneByEmail(collaboratorDto.getEmail(), noteId);
			if (note != null && collaboratorDB == null) {
//				collaborator.setEmail(collaboratorDB.getEmail());
				BeanUtils.copyProperties(collaboratorDto, collaborator);
				collaborator.setNote(note);
				collaboratorRepository.addCollaborator(collaborator.getId(), collaborator.getEmail(), noteId);
				return collaborator;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Collaborator deleteCollaborator(long collaboratorId, String token, long noteId) {
		try {
			long userId = jwtGenerator.parse(token);
			User user = userRepository.findById(userId);
			if (user != null) {
				Notes note = noteRepository.findById(noteId);
				if (note != null) {
					Collaborator collaborator = collaboratorRepository.findById(collaboratorId);
					if (collaborator != null) {
						collaboratorRepository.deleteCollaborator(collaboratorId, noteId);
						return collaborator;
					}
				}
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Collaborator> getNoteCollaborators(String token, long noteId) {
		try {
			long userId = jwtGenerator.parse(token);
			if (userId != 0) {
				List<Notes> note = noteRepository.searchAllNotesByNoteId(userId, noteId);
				if (note != null) {
					return collaboratorRepository.getAllNoteCollaborators(noteId);
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}