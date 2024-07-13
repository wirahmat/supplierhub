package com.supplierhub.supplierhub.common.model.response;

public class UserResponse extends MasterResponse {

	private String fullname;
	private String email;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
