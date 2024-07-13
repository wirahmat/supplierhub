package com.supplierhub.supplierhub.service;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.common.model.request.login.LoginRequest;
import com.supplierhub.supplierhub.common.model.request.user.CreateUserRequest;
import com.supplierhub.supplierhub.common.model.request.user.UpdateUserRequest;
import com.supplierhub.supplierhub.common.model.response.LoginResponse;
import com.supplierhub.supplierhub.common.model.response.UserResponse;
import com.supplierhub.supplierhub.persistence.entity.User;

public interface UserService {

	String getToken(String userId);
	
	String validateToken();
	
	void validateIdExist(String id);

	void validateIdActive(String id);

	void validateBkNotExist(String email);

	void validateVersion(String id, Long version);

	List<UserResponse> getAll();

	Optional<User> getEntityById(String id);

	User getValidatedEntityById(String id);
	
	UserResponse getById(String id);
	
	void add(CreateUserRequest data);
	
	void edit(UpdateUserRequest data);
	
	void delete(String id);
	
	void delete(List<String> ids);
	
	LoginResponse login(LoginRequest loginRequest);
	
}
