package com.bridgelabz.fundoonotes.exception;

import java.io.IOException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bridgelabz.fundoonotes.response.Responses;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(value = { IOException.class })
	public Responses badRequest(Exception exception) {
		return new Responses("Bad Request", 400);
	}

	@ExceptionHandler(value = { Exception.class })
	public Responses unknowException(Exception exception) {
		return new Responses(exception.getMessage(), 400);
	}
}
