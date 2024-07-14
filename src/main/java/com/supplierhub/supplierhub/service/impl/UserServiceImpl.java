package com.supplierhub.supplierhub.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.supplierhub.supplierhub.common.model.request.login.LoginRequest;
import com.supplierhub.supplierhub.common.model.request.user.CreateUserRequest;
import com.supplierhub.supplierhub.common.model.request.user.UpdateUserRequest;
import com.supplierhub.supplierhub.common.model.response.LoginResponse;
import com.supplierhub.supplierhub.common.model.response.UserResponse;
import com.supplierhub.supplierhub.persistence.entity.User;
import com.supplierhub.supplierhub.persistence.repository.UserRepository;
import com.supplierhub.supplierhub.service.JwtService;
import com.supplierhub.supplierhub.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository repo;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository repo, JwtService jwtService, PasswordEncoder passwordEncoder) {
		this.repo = repo;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOpt = repo.findByEmail(username);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
					new ArrayList<>());
		}
		throw new UsernameNotFoundException("Email tidak ditemukan");
	}

	@Override
	public String getToken(String userId) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 1);

		Map<String, Object> claims = new HashMap<>();
		claims.put("exp", cal.getTime());
		claims.put("id", userId);
		String response = jwtService.generateJwt(claims);
		return response;
	}

	@Override
	public String validateToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null || auth.getPrincipal() == null)
			throw new RuntimeException("Invalid Login");

		return auth.getPrincipal().toString();
	}

	@Override
	public void validateIdExist(String id) {
		if (!repo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user id is not found ");
		}
	}

	@Override
	public void validateIdActive(String id) {
		User user = getEntityById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "user id is not found "));
		if (Boolean.FALSE.equals(user.getIsActive())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not active");
		}
	}

	@Override
	public void validateBkNotExist(String email) {
		if (repo.existsByEmail(email)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user with same code is exists ");
		}
	}

	@Override
	public void validateVersion(String id, Long version) {
		User user = getEntityById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not active"));
		if (!user.getVersion().equals(version)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user version does not matched");
		}
	}

	@Override
	public List<UserResponse> getAll() {
		List<User> users = repo.findAll();
		List<UserResponse> userResponse = users.stream().map(this::mapToResponse).toList();
		return userResponse;
	}

	@Override
	public Optional<User> getEntityById(String id) {
		return repo.findById(id);
	}

	@Override
	public User getValidatedEntityById(String id) {
		return getEntityById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not exists"));
	}

	@Override
	public UserResponse getById(String id) {
		User user = getValidatedEntityById(id);
		return mapToResponse(user);
	}

	@Override
	@Transactional
	public void add(CreateUserRequest data) {
		validateBkNotExist(data.getEmail());

		User user = new User();
		BeanUtils.copyProperties(data, user);

		String passEncode = passwordEncoder.encode(data.getPassword());
		user.setPassword(passEncode);
		user.setIsActive(true);

		repo.save(user);
	}

	@Override
	@Transactional
	public void edit(UpdateUserRequest data) {
		validateIdExist(data.getId());
		User user = getValidatedEntityById(data.getId());
		validateVersion(user.getId(), data.getVersion());
		BeanUtils.copyProperties(data, user);

		String passEncode = passwordEncoder.encode(data.getPassword());
		user.setPassword(passEncode);

		repo.saveAndFlush(user);
	}

	@Override
	@Transactional
	public void delete(String id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional
	public void delete(List<String> ids) {
		for (String id : ids) {
			delete(id);
		}
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		Optional<User> userOpt = repo.findByEmail(loginRequest.getEmail());

		LoginResponse loginResponse = new LoginResponse();
		if(userOpt.isPresent()) {
			User user = userOpt.get();
			
			loginResponse.setId(user.getId());
			loginResponse.setFullName(user.getFullName());
		}

		return loginResponse;
	}

	private UserResponse mapToResponse(User user) {
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(user, userResponse);

		return userResponse;
	}

}
