package com.supplierhub.supplierhub.common.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebResponse<T> {

	/**
	 * Code , usually same as HTTP Code
	 */
	@JsonProperty("code")
	private Integer code;

	/**
	 * Status, usually same as HTTP status
	 */
	@JsonProperty("status")
	private String status;

	/**
	 * Response data
	 */
	@JsonProperty("data")
	private T data;

	public WebResponse(Integer code, String status, T data) {
		this.code = code;
		this.status = status;
		this.data = data;
	}
	
	
	
	
}
