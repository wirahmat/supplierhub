package com.supplierhub.supplierhub.common.helper;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.supplierhub.supplierhub.common.model.response.WebResponse;

public class ResponseHelper {

	public static <T> WebResponse<T> ok(T data) {
		return ResponseHelper.status(HttpStatus.OK, data, null, null);
	}

	public static <T> WebResponse<T> status(HttpStatus status, T data, Map<String, List<String>> errors,
			Map<String, Object> metadata) {
		WebResponse<T> webResponse = new WebResponse<T>(status.value(), status.name(), data);
		return webResponse;
	}
}
