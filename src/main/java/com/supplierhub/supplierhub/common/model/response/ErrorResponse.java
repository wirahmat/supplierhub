package com.supplierhub.supplierhub.common.model.response;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	HttpStatus httpStatus;
	String message;

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
