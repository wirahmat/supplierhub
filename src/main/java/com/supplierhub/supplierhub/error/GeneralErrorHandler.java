package com.supplierhub.supplierhub.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.supplierhub.supplierhub.error.response.SimplyfiyResponseException;

@ControllerAdvice
public class GeneralErrorHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<String>> handleValidation(MethodArgumentNotValidException ex){
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(e -> {
			errors.add(e.getDefaultMessage());
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<SimplyfiyResponseException> handleResponseException(ResponseStatusException ex){
		SimplyfiyResponseException errors = new SimplyfiyResponseException();
		
		errors.setHttpStatus(ex.getStatusCode().toString());
		errors.setMessage(ex.getReason());
		
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
}
