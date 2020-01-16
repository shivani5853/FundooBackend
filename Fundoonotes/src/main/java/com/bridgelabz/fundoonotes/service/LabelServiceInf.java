package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.model.Labels;

public interface LabelServiceInf {

	public Labels create(LabelDto label, String token);

	public Labels deleteLabel(LabelDto label, String token);

	public Labels labelMapToNote(LabelDto label, String token, long noteId);

}
