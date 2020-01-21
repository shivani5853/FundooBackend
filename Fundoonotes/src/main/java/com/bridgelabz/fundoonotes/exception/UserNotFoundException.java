package com.bridgelabz.fundoonotes.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception{

	UserNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
