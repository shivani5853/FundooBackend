package com.bridgelabz.fundoonotes.serviceimplementation;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.LabelNoteReopsitory;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.LabelServiceInf;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LabelServiceImplementation implements LabelServiceInf {

	@Autowired
	private JwtGenerator jwtGenerator;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private LabelNoteReopsitory labelNoteReopsitory;

	@Override
	public Labels create(LabelDto labelDto, String token) {
		try {
			long userId = jwtGenerator.parse(token);
			User user = userRepository.findById(userId);
			System.out.println(user);
			if (user != null) {
				String labelName = labelDto.getLableName();
				Labels label = labelRepository.findByName(labelName);
				System.out.println("labelName" + labelName);
				System.out.println("label" + label);
				if (label == null) {
					Labels labelNew = new Labels();
					labelNew.setLabelName(labelName);
					System.out.println("labelNew" + labelNew);
					BeanUtils.copyProperties(labelDto, labelName);
					labelNew.setLabelUser(user);
					labelRepository.insertData(labelNew.getLabelName(), userId);
					return labelNew;
				} else {
					return null;
				}
			}
			return null;

		} catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Labels deleteLabel(LabelDto label, String token) {
		try {
			long userId = jwtGenerator.parse(token);
			User user = userRepository.findById(userId);
			if (user != null) {
				Notes note = noteRepository.findById(userId);
				Labels labelNew = new Labels();
				labelNew.setLabelName(label.getLableName());
				labelRepository.deleteLabel(label.getLableName(), user.getUserId());
				return labelNew;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Labels labelMapToNote(LabelDto label, String token, long noteId) {
		try {
			long userId = jwtGenerator.parse(token);
			User user = userRepository.findById(userId);
			Notes note = noteRepository.findById(noteId);
			if (user != null) {
				if (note != null) {
					String labelName = label.getLableName();
					log.info(labelName);
					System.out.println("labelName" + labelName);
					Labels labelInfo = labelRepository.findByName(labelName);
					System.out.println("labelInfo" + labelInfo);
					if (labelInfo != null) {
						Labels labelNew = new Labels();
						System.out.println("labelName" + labelName);
//						labelNew.setLabelName(label.getLableName());

						BeanUtils.copyProperties(label, labelNew);

						labelNew.setLabelUser(user);
						System.out.println(labelNew);
						Labels labelForMap = labelRepository.findByName(labelNew.getLabelName());
						System.out.println(labelForMap);
						labelNoteReopsitory.labelMapToNote(noteId, labelForMap.getLabelId());
						return labelNew;
					}
				}
			} else {
				return null;
			}
		} catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Labels updateLabel(String token, long noteId,long lableId) {
		try {
			long userId = jwtGenerator.parse(token);
			User user = userRepository.findById(userId);
			if (user != null) {
				Notes note = noteRepository.findById(noteId);
				System.out.println(note);
				if (note != null) {
					Labels label=new Labels();
					label.setLabelId(lableId);
					Labels labelNew=userRepository.findBylableId(label.getLabelId());
					System.out.println(labelNew);
					if(labelNew!=null)
					{
					labelRepository.updateLabel(label.getLabelName(),userId,lableId);
					return label;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
