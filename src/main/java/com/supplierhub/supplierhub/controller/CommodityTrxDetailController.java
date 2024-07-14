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

import com.supplierhub.supplierhub.common.model.request.commoditytrx.CreateCommodityTrxDetailRequest;
import com.supplierhub.supplierhub.common.model.request.commoditytrx.UpdateCommodityTrxDetailRequest;
import com.supplierhub.supplierhub.common.model.response.CommodityTrxDetailResponse;
import com.supplierhub.supplierhub.service.CommodityTrxDetailService;

@RestController
@RequestMapping({ "/api/commodity-trx-detail" })
public class CommodityTrxDetailController {

	private final CommodityTrxDetailService commodityTrxDetailService;

	public CommodityTrxDetailController(CommodityTrxDetailService commodityTrxDetailService) {
		this.commodityTrxDetailService = commodityTrxDetailService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CommodityTrxDetailResponse>> getAll(@RequestParam String commodityTrxId) {
		var result = commodityTrxDetailService.getAllByHeaderId(commodityTrxId);
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommodityTrxDetailResponse> getById(@PathVariable String id) {
		var result = commodityTrxDetailService.getById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> add(@RequestBody CreateCommodityTrxDetailRequest request) {
		commodityTrxDetailService.add(request);
		return ResponseEntity.ok("commodity trx detail has been added successfully");
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> edit(@RequestBody UpdateCommodityTrxDetailRequest request) {
		commodityTrxDetailService.edit(request);
		return ResponseEntity.ok("commodity trx detail has been edited successfully");
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable String id) {
		commodityTrxDetailService.delete(id);
		return ResponseEntity.ok("commodity trx detail has been deleted successfully");
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@RequestBody List<String> ids) {
		commodityTrxDetailService.delete(ids);
		return ResponseEntity.ok("commodity trx detail(s) has been deleted successfully");
	}
}
