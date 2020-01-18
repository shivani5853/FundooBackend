package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.CollaboratorDto;

public interface CollaboratorServiceInf {

	CollaboratorDto addCollaborator(CollaboratorDto collaboratorDto, Long userId, String token);

}
