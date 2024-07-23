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

import com.supplierhub.supplierhub.common.model.request.commoditytrx.CreateCommodityTrxRequest;
import com.supplierhub.supplierhub.common.model.request.commoditytrx.UpdateCommodityTrxRequest;
import com.supplierhub.supplierhub.common.model.response.CommodityTrxResponse;
import com.supplierhub.supplierhub.service.CommodityTrxService;

import jakarta.validation.Valid;

@RestController
@RequestMapping({ "/api/commodity-trx" })
public class CommodityTrxController {

	private final CommodityTrxService commodityTrxService;

	public CommodityTrxController(CommodityTrxService commodityTrxService) {
		this.commodityTrxService = commodityTrxService;
	}

	@GetMapping()
	public ResponseEntity<List<CommodityTrxResponse>> getAll() {
		var result = commodityTrxService.getAll();
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CommodityTrxResponse> getById(@PathVariable String id) {
		var result = commodityTrxService.getById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping()
	public ResponseEntity<String> add(@RequestBody CreateCommodityTrxRequest request) {
		commodityTrxService.add(request);
		return ResponseEntity.ok("commodity trx has been added successfully");
	}

	@PutMapping()
	public ResponseEntity<String> edit(@RequestBody @Valid UpdateCommodityTrxRequest request) {
		commodityTrxService.edit(request);
		return ResponseEntity.ok("commodity trx has been edited successfully");
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		commodityTrxService.delete(id);
		return ResponseEntity.ok("commodity trx has been deleted successfully");
	}

	@DeleteMapping()
	public ResponseEntity<String> delete(@RequestBody List<String> ids) {
		commodityTrxService.delete(ids);
		return ResponseEntity.ok("commodity trx(s) has been deleted successfully");
	}

}
