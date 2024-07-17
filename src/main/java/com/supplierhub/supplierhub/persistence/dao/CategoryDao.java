package com.supplierhub.supplierhub.persistence.dao;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.persistence.entity.Category;

public interface CategoryDao {

	boolean existsById(String id);
	
	boolean existsByCode(String code);
	
	List<Category> getAll();
	
	Optional<Category> findById(String id);
	
}
