package com.supplierhub.supplierhub.persistence.dao;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.persistence.entity.User;

public interface UserDao{

	boolean existsById(String id);

	boolean existsByEmail(String email);

	List<User> getAll();

	Optional<User> findById(String id);
	
	Optional<User> findByEmail(String email);
}
