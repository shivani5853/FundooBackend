package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.CollaboratorDto;
import com.bridgelabz.fundoonotes.model.Collaborator;

public interface CollaboratorServiceInf {

	Collaborator addCollaborator(CollaboratorDto collaboratorDto, String token, long noteId);

	Collaborator deleteCollaborator(long collabortorId, String token, long noteId);

	List<Collaborator> getNoteCollaborators(String token, long noteId);


}