/******************************************************************************
 *  Purpose: Utility
 *
 *  @author  Shivani Kumari
 *  @version 1.0
 *  @since   29-12-2019
 *
 ******************************************************************************/

/*
 * PACKAGE NAME
 */
package com.bridgelabz.fundoonotes.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/*
 * IMPORT STATEMENTS
 */

public class Utility {

	@Autowired
	JavaMailSender javaMailSender;

	public void sendMail(String email , String response) {
		try {
			SimpleMailMessage simpleMsg = new SimpleMailMessage();
			simpleMsg.setTo(email);
			simpleMsg.setSubject("Forget password");
			simpleMsg.setText(response);
			javaMailSender.send(simpleMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}