package com.supplierhub.supplierhub.service;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.common.model.request.category.CreateCategoryRequest;
import com.supplierhub.supplierhub.common.model.request.category.UpdateCategoryRequest;
import com.supplierhub.supplierhub.common.model.response.CategoryResponse;
import com.supplierhub.supplierhub.persistence.entity.Category;

public interface CategoryService {

	List<CategoryResponse> getAll();

	Optional<Category> getEntityById(String id);

	Category getValidatedEntityById(String id);
	
	CategoryResponse getById(String id);
	
	void add(CreateCategoryRequest data);
	
	void edit(UpdateCategoryRequest data);
	
	void delete(String id);
	
	void delete(List<String> ids);
	
}
