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

import com.supplierhub.supplierhub.common.model.request.commodity.CreateCommodityRequest;
import com.supplierhub.supplierhub.common.model.request.commodity.UpdateCommodityRequest;
import com.supplierhub.supplierhub.common.model.response.CommodityResponse;
import com.supplierhub.supplierhub.service.CommodityService;

@RestController
@RequestMapping({ "/api/commodity" })
public class CommodityController {

	private final CommodityService commodityService;
	
	public CommodityController(CommodityService commodityService) {
		this.commodityService = commodityService;
	}

	@GetMapping(produces  = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CommodityResponse>> getAll(){
		var result = commodityService.getAll();
		return ResponseEntity.ok(result);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommodityResponse> getById(@PathVariable String id){
		var result = commodityService.getById(id);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> add(@RequestBody CreateCommodityRequest request){
		commodityService.add(request);
		return ResponseEntity.ok("commodity has been added successfully");
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> edit(@RequestBody UpdateCommodityRequest request){
		commodityService.edit(request);
		return ResponseEntity.ok("commodity has been edited successfully");
	}
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable String id){
		commodityService.delete(id);
		return ResponseEntity.ok("commodity has been deleted successfully");
	}
	
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@RequestBody List<String> ids){
		commodityService.delete(ids);
		return ResponseEntity.ok("commodity(s) has been deleted successfully");
	}
}
