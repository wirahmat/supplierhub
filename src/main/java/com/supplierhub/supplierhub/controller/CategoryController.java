package com.supplierhub.supplierhub.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supplierhub.supplierhub.common.helper.ResponseHelper;
import com.supplierhub.supplierhub.common.model.request.category.CreateCategoryRequest;
import com.supplierhub.supplierhub.common.model.request.category.UpdateCategoryRequest;
import com.supplierhub.supplierhub.common.model.response.CategoryResponse;
import com.supplierhub.supplierhub.common.model.response.WebResponse;
import com.supplierhub.supplierhub.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping({ "/api/category" })
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping()
	public ResponseEntity<WebResponse<List<CategoryResponse>>> getAll() {
		var result = categoryService.getAll();
		return ResponseEntity.ok(ResponseHelper.ok(result));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<WebResponse<CategoryResponse>> getById(@PathVariable String id) {
		var result = categoryService.getById(id);
		return ResponseEntity.ok(ResponseHelper.ok(result));
	}

	@PostMapping()
	public ResponseEntity<WebResponse<String>> add(@RequestBody CreateCategoryRequest request) {
		categoryService.add(request);
		return ResponseEntity.ok(ResponseHelper.ok("category has been added successfully"));
	}

	@PutMapping()
	public ResponseEntity<WebResponse<String>> edit(@RequestBody @Valid UpdateCategoryRequest request) {
		categoryService.edit(request);
		return ResponseEntity.ok(ResponseHelper.ok("category has been edited successfully"));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<WebResponse<String>> delete(@PathVariable String id) {
		categoryService.delete(id);
		return ResponseEntity.ok(ResponseHelper.ok("category has been deleted successfully"));
	}

	@DeleteMapping()
	public ResponseEntity<WebResponse<String>> delete(@RequestBody List<String> ids) {
		categoryService.delete(ids);
		return ResponseEntity.ok(ResponseHelper.ok("category(s) has been deleted successfully"));
	}
}
