package com.bridgelabz.fundoonotes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.response.Responses;
import com.bridgelabz.fundoonotes.service.UserServiceInf;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@RestController("/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserServiceInf service;

	@Autowired
	private JwtGenerator jwtGenerator;

	@PostMapping("/register")
	public ResponseEntity<Responses> register(@RequestBody UserDto userDto){

		User user = service.register(userDto);
		if (user != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Responses("Registration Successfully", 200, userDto));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Responses("User Already Exist", 400, userDto));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Responses> login(@RequestBody UserLoginDto userLogin) {
		User user = service.login(userLogin);
		if (user != null) {
			String token = jwtGenerator.jwtToken(user.getUserId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Login Successfully", userLogin.getEmail())
					.body(new Responses(token, 200, userLogin));

		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Responses("Sorry Something went wrong!!!", 400, userLogin));
		}
	}

	@GetMapping("/verify/{token}")
	public ResponseEntity<Responses> verifyUser(@PathVariable("token") String token) {
		System.out.println("Token for verify " + token);
		User user = service.verify(token);

		return (user) != null ? ResponseEntity.status(HttpStatus.ACCEPTED).body(new Responses("Verified", 200))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Responses("Not verified", 400));
	}

	@PostMapping("/forgetPassword")
	public ResponseEntity<Responses> forgetPassword(@RequestHeader String email) {
		logger.info("Email:" + email);
		System.out.println(email);
		User user = service.forgetPassword(email);

		return (user) != null ? ResponseEntity.status(HttpStatus.ACCEPTED).body(new Responses("User Exist", 200))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Responses("User Not found", 400));
	}

	@PostMapping("/updatePassword/{token}")
	public ResponseEntity<Responses> updatePassword(@RequestBody UpdatePassword password,
			@PathVariable("token") String token) {
		System.out.println("12");
		logger.info("Token:" + token);
		System.out.println("password" + password.getConformPassword());
		boolean verified = service.updatePassword(password, token);

		return (verified)
				? ResponseEntity.status(HttpStatus.ACCEPTED).body(new Responses("Password Changed Sucessfully!!!", 200))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Responses("Password Not Update", 400));
	}
}
