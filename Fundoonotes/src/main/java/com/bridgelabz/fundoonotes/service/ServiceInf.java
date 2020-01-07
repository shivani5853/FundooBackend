package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Map;

import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.model.User;

public interface ServiceInf {
	public boolean register(UserDto userDto);

	public User login(UserLoginDto userLogin);

	public List<User> findAllDetails();

//	public Map<String, Object> findByUserId(int id);

	public boolean verify(String token);

	public boolean forgetPassword(String email);

	boolean updatePassword(UpdatePassword password, String token);
}
