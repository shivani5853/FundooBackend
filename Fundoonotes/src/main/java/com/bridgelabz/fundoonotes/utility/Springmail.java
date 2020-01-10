package com.bridgelabz.fundoonotes.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class Springmail {
	@Autowired
	JavaMailSender javaMailSender;

	public void sendMail(String email, String response) {
		try {
			SimpleMailMessage simpleMsg = new SimpleMailMessage();
			simpleMsg.setTo(email);
			simpleMsg.setSubject("Verify mail");
			simpleMsg.setText(response);
			javaMailSender.send(simpleMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
