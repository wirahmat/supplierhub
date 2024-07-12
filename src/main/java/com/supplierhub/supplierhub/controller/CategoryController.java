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

import com.supplierhub.supplierhub.common.model.request.category.CreateCategoryRequest;
import com.supplierhub.supplierhub.common.model.request.category.UpdateCategoryRequest;
import com.supplierhub.supplierhub.common.model.response.CategoryResponse;
import com.supplierhub.supplierhub.service.CategoryService;

@RestController
@RequestMapping({ "/api/category" })
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoryResponse>> getAll() {
		var result = categoryService.getAll();
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryResponse> getById(@PathVariable String id) {
		var result = categoryService.getById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> add(@RequestBody CreateCategoryRequest request) {
		categoryService.add(request);
		return ResponseEntity.ok("category has been added successfully");
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> edit(@RequestBody UpdateCategoryRequest request) {
		categoryService.edit(request);
		return ResponseEntity.ok("category has been edited successfully");
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable String id) {
		categoryService.delete(id);
		return ResponseEntity.ok("category has been deleted successfully");
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@RequestBody List<String> ids) {
		categoryService.delete(ids);
		return ResponseEntity.ok("category(s) has been deleted successfully");
	}
}
