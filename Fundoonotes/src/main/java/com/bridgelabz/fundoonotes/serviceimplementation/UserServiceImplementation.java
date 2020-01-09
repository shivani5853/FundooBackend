package com.bridgelabz.fundoonotes.serviceimplementation;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserServiceInf;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;
import com.bridgelabz.fundoonotes.utility.Springmail;

@Service
public class UserServiceImplementation implements UserServiceInf {

	private static final Logger loggger = LoggerFactory.getLogger(UserServiceImplementation.class);

	private User user = new User();

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtGenerator jwtGenerator;

	@Autowired
	private Springmail mail	;

	private UpdatePassword updatePassword = new UpdatePassword();

	@Override
	public User register(UserDto userDto) {
		try {

			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setEmail(userDto.getEmail());
			user.setPhoneNumber(userDto.getPhoneNumber());
			user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));

//			BeanUtils.copyProperties(userDto, user);
			userRepository.save(user);
			User isUserAvailable = userRepository.FindByEmail(userDto.getEmail());
			String email = user.getEmail();
			String response = "http://localhost:8080/verify/" + jwtGenerator.jwtToken(isUserAvailable.getId());
			mail.sendMail(email, response);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User login(UserLoginDto userLogin) {
		User user = userRepository.checkByEmail(userLogin.getEmail());
		System.out.println(user + userLogin.getEmail());
		if (user.getEmail().equalsIgnoreCase(userLogin.getEmail())) {
			if(user.isVerified())
			{
			boolean ispasswordMatch = BCrypt.checkpw(userLogin.getPassword(), user.getPassword());
			if (ispasswordMatch) {
				System.out.println("sucessfully login");
				return user;
			} 
			}else {
				return null;
			}

		}

		return user;
	}

	@Override
	public User verify(String token) {
		try {
			System.out.println("Inside");
			loggger.info("Id Varification", (long) jwtGenerator.parse(token));
			long id = jwtGenerator.parse(token);
			System.out.println(token);
			User isIdValied = userRepository.findById(id);
			if (!isIdValied.isVerified()) {
				userRepository.updateIsVarified(id);
				System.out.println("save details");
				return user;
			} else {
				System.out.println("already varified");
				return user;
			}
		} catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<User> findAllDetails() {
		return userRepository.findAll();
	}

	@Override
	public User forgetPassword(String email) {
		User isUserAvailable = userRepository.FindByEmail(email);
		System.out.println(isUserAvailable + email);
		if (isUserAvailable != null && isUserAvailable.isVerified() == true) {
			try {
				System.out.println("inside");
				String response = "http://localhost:8080/updatePassword/"
						+ jwtGenerator.jwtToken(isUserAvailable.getId());
				mail.sendMail(email, response);
				System.out.println("mail Send");
				return user;
			} catch (JWTVerificationException | IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean updatePassword(UpdatePassword password, String token) {
		try {
			loggger.info("Token:" + jwtGenerator.parse(token));
			System.out.println(password);
			if (password.getPassword().equals(password.getConformPassword())) {
				long id = jwtGenerator.parse(token);
				System.out.println("Password Correct");
				User isIdAvailable = userRepository.findById(id);
				if (isIdAvailable.isVerified()) {
					isIdAvailable.setPassword(new BCryptPasswordEncoder().encode(password.getConformPassword()));
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
