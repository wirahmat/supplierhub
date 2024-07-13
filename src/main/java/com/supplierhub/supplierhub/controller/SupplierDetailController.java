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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supplierhub.supplierhub.common.model.request.supplier.CreateSupplierDetailRequest;
import com.supplierhub.supplierhub.common.model.request.supplier.UpdateSupplierDetailRequest;
import com.supplierhub.supplierhub.common.model.response.SupplierDetailResponse;
import com.supplierhub.supplierhub.service.SupplierDetailService;

@RestController
@RequestMapping({ "/api/supplier-detail" })
public class SupplierDetailController {

	private final SupplierDetailService supplierDetailService;

	public SupplierDetailController(SupplierDetailService supplierDetailService) {
		this.supplierDetailService = supplierDetailService;
	}
	
	@GetMapping(value= "commodity", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SupplierDetailResponse>> getAllByCommodity(@RequestParam String commodityId) {
		var result = supplierDetailService.getAllByCommodity(commodityId);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping(value= "supplier", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SupplierDetailResponse>> getAllBySupplier(@RequestParam String supplierId) {
		var result = supplierDetailService.getAllBySupplier(supplierId);
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SupplierDetailResponse> getById(@PathVariable String id) {
		var result = supplierDetailService.getById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> add(@RequestBody CreateSupplierDetailRequest request) {
		supplierDetailService.add(request);
		return ResponseEntity.ok("supplier detail has been added successfully");
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> edit(@RequestBody UpdateSupplierDetailRequest request) {
		supplierDetailService.edit(request);
		return ResponseEntity.ok("supplier detail has been edited successfully");
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable String id) {
		supplierDetailService.delete(id);
		return ResponseEntity.ok("supplier detail has been deleted successfully");
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@RequestBody List<String> ids) {
		supplierDetailService.delete(ids);
		return ResponseEntity.ok("supplier detail(s) has been deleted successfully");
	}
}
