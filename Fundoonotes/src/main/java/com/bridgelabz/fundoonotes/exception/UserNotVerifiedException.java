package com.bridgelabz.fundoonotes.exception;

@SuppressWarnings("serial")
public class UserNotVerifiedException extends Exception {

	public UserNotVerifiedException(String exceptionMessage) {
		super(exceptionMessage);
	}
}