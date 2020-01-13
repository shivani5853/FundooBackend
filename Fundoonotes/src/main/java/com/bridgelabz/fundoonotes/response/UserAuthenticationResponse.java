package com.bridgelabz.fundoonotes.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationResponse {
	private String token;
	private int statusCode;
	private Object obj;
}
