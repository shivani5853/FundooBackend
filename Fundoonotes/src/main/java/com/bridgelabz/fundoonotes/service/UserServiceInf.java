package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.User;

public interface UserServiceInf {
	public User register(UserDto userDto)throws UserException;

	public User login(UserLoginDto userLogin);

	public List<User> findAllDetails();

//	public Map<String, Object> findByUserId(int id);

	public User verify(String token);

	public User forgetPassword(String email);

	boolean updatePassword(UpdatePassword password, String token);
}
