package com.bookend.authorizationserver.payload;

public class NewPasswordRequest {

	private String password;

	public NewPasswordRequest(String password) {
		this.password = password;
	}

	public NewPasswordRequest() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	} 
}
