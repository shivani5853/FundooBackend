package com.bridgelabz.fundoonotes.serviceimplementation;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ServiceInf;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class ServiceImplementation implements ServiceInf {

	private static final Logger loggger = LoggerFactory.getLogger(ServiceImplementation.class);
	@Autowired
	private User user;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtGenerator jwtGenerator;

	@Autowired
	private Utility util;

	@Autowired
	private UpdatePassword updatePassword;

	@Override
	public boolean register(UserDto userDto) {
		try {

//			user.setFirstName(userDto.getFirstName());
//			user.setLastName(userDto.getLastName());
//			user.setEmail(userDto.getEmail());
//			user.setPhoneNumber(userDto.getPhoneNumber());
//			user.setPassword(userDto.getPassword());

			BeanUtils.copyProperties(userDto, user);
			userRepository.save(user);
			User isUserAvailable = userRepository.FindByEmail(userDto.getEmail());
			String email = user.getEmail();
			String response = "http://localhost:8080/user/verify/" + jwtGenerator.jwtToken(isUserAvailable.getId());
			util.sendMail(email, response);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User login(UserLoginDto userLogin) {
		User user = userRepository.checkByEmail(userLogin.getEmail());
		System.out.println(user + userLogin.getEmail());
		if (user.getEmail().equalsIgnoreCase(userLogin.getEmail())) {
			System.out.println("sucessfully login");
		}

		return user;
	}

	@Override
	public boolean verify(String token) {
		try {
			System.out.println("Inside");
			loggger.info("Id Varification", (long) jwtGenerator.parse(token));
			long id = jwtGenerator.parse(token);
			System.out.println(token);
			User isIdValied = userRepository.findById(id);
			if (!isIdValied.isVerified()) {
				userRepository.updateIsVarified(id);
				System.out.println("save details");
				return true;
			} else {
				System.out.println("already varified");
				return true;
			}
		} catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<User> findAllDetails() {
		return userRepository.findAll();
	}

	@Override
	public boolean forgetPassword(String email) {
		User isUserAvailable = userRepository.FindByEmail(email);
		System.out.println(isUserAvailable + email);
		if (isUserAvailable != null && isUserAvailable.isVerified() == true) {
			try {
				System.out.println("inside");
				String response = "http://localhost:8080/user/updatePassword/"
						+ jwtGenerator.jwtToken(isUserAvailable.getId());
				util.sendMail(email, response);
				return true;
			} catch (JWTVerificationException | IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean updatePassword(UpdatePassword password, String token) {
		try {
			loggger.info("Token:" + jwtGenerator.parse(token));
			if (password.getPassword().equals(password.getConformPassword())) {
				long id = jwtGenerator.parse(token);
				System.out.println("Password Correct");
				User isIdAvailable = userRepository.findById(id);
				if (isIdAvailable.isVerified()) {
					isIdAvailable.setPassword(password.getConformPassword());
					System.out.println("password set into model");
					userRepository.updatePassword(password.getPassword(), id);
					System.out.println("details save in the database");
					return true;
				} else {
					System.out.println("Password not set into model");
					return false;
				}
			} else {
				System.out.println("Password Not correct");
				return false;
			}
		} catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return false;
	}

}
