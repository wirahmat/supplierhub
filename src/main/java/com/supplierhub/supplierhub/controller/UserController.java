package com.supplierhub.supplierhub.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supplierhub.supplierhub.common.model.request.user.CreateUserRequest;
import com.supplierhub.supplierhub.common.model.request.user.UpdateUserRequest;
import com.supplierhub.supplierhub.common.model.response.UserResponse;
import com.supplierhub.supplierhub.service.UserService;

@RestController
@RequestMapping({ "/api/users" })
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserResponse>> getAll() {
		var result = userService.getAll();
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> getById(@PathVariable String id) {
		var result = userService.getById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> add(@RequestBody CreateUserRequest request) {
		userService.add(request);
		return ResponseEntity.ok("user has been added successfully");
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> edit(@RequestBody UpdateUserRequest request) {
		userService.edit(request);
		return ResponseEntity.ok("user has been edited successfully");
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable String id) {
		userService.delete(id);
		return ResponseEntity.ok("user has been deleted successfully");
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@RequestBody List<String> ids) {
		userService.delete(ids);
		return ResponseEntity.ok("user(s) has been deleted successfully");
	}
	
}
