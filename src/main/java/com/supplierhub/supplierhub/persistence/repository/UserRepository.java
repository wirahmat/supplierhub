package com.supplierhub.supplierhub.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

	boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);
}
