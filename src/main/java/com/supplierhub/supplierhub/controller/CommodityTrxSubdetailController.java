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

import com.supplierhub.supplierhub.common.model.request.commoditytrx.CreateCommodityTrxSubdetailRequest;
import com.supplierhub.supplierhub.common.model.request.commoditytrx.UpdateCommodityTrxSubdetailRequest;
import com.supplierhub.supplierhub.common.model.response.CommodityTrxSubdetailResponse;
import com.supplierhub.supplierhub.service.CommodityTrxSubdetailService;

@RestController
@RequestMapping({ "/api/commodity-trx-subdetail" })
public class CommodityTrxSubdetailController {

	private final CommodityTrxSubdetailService commodityTrxSubdetailService;

	public CommodityTrxSubdetailController(CommodityTrxSubdetailService commodityTrxSubdetailService) {
		this.commodityTrxSubdetailService = commodityTrxSubdetailService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CommodityTrxSubdetailResponse>> getAll(@RequestParam String commodityTrxDetailId) {
		var result = commodityTrxSubdetailService.getAllByDetailId(commodityTrxDetailId);
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommodityTrxSubdetailResponse> getById(@PathVariable String id) {
		var result = commodityTrxSubdetailService.getById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> add(@RequestBody CreateCommodityTrxSubdetailRequest request) {
		commodityTrxSubdetailService.add(request);
		return ResponseEntity.ok("commodity trx subdetail has been added successfully");
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> edit(@RequestBody UpdateCommodityTrxSubdetailRequest request) {
		commodityTrxSubdetailService.edit(request);
		return ResponseEntity.ok("commodity trx subdetail has been edited successfully");
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable String id) {
		commodityTrxSubdetailService.delete(id);
		return ResponseEntity.ok("commodity trx subdetail has been deleted successfully");
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@RequestBody List<String> ids) {
		commodityTrxSubdetailService.delete(ids);
		return ResponseEntity.ok("commodity trx subdetail(s) has been deleted successfully");
	}

}
