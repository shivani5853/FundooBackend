package com.bridgelabz.fundoonotes.exception;

public class UserException extends Throwable {

	public UserException() {
		super();
	}

	public void userException() throws UserException {
		try {
			throw new UserException();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	class UserExceptionChild extends UserException {

		public UserExceptionChild() {
			super();
		}

	}
}