package com.supplierhub.supplierhub.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supplierhub.supplierhub.common.model.request.login.LoginRequest;
import com.supplierhub.supplierhub.common.model.request.login.TokenReqDto;
import com.supplierhub.supplierhub.common.model.response.LoginResponse;
import com.supplierhub.supplierhub.service.UserService;

@RestController
@RequestMapping({ "/api/login" })
public class LoginController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;

	public LoginController(UserService userService, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginRequest user) {
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

		authenticationManager.authenticate(auth);
		LoginResponse loginRes = userService.login(user);

		TokenReqDto tokenReqDto = new TokenReqDto();
		tokenReqDto.setId(loginRes.getId());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String token = userService.getToken(loginRes.getId());

		loginRes.setToken(token);

		return new ResponseEntity<>(loginRes, HttpStatus.OK);
	}

}
