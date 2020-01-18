package com.bridgelabz.fundoonotes.serviceimplementation;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.CollaboratorDto;
import com.bridgelabz.fundoonotes.service.CollaboratorServiceInf;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CollaboratorServiceImplementation implements CollaboratorServiceInf{

	@Override
	public CollaboratorDto addCollaborator(CollaboratorDto collaboratorDto, Long userId, String token) {
		try {
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
