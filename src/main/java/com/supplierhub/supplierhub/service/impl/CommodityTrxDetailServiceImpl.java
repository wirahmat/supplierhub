package com.supplierhub.supplierhub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.supplierhub.supplierhub.common.model.request.commoditytrx.CreateCommodityTrxDetailRequest;
import com.supplierhub.supplierhub.common.model.request.commoditytrx.CreateCommodityTrxSubdetailRequest;
import com.supplierhub.supplierhub.common.model.request.commoditytrx.UpdateCommodityTrxDetailRequest;
import com.supplierhub.supplierhub.common.model.response.CommodityTrxDetailResponse;
import com.supplierhub.supplierhub.common.model.response.CommodityTrxSubdetailResponse;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrx;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrxDetail;
import com.supplierhub.supplierhub.persistence.entity.Supplier;
import com.supplierhub.supplierhub.persistence.repository.CommodityTrxDetailRepository;
import com.supplierhub.supplierhub.service.CommodityTrxDetailService;
import com.supplierhub.supplierhub.service.CommodityTrxService;
import com.supplierhub.supplierhub.service.CommodityTrxSubdetailService;
import com.supplierhub.supplierhub.service.SupplierService;

@Service
public class CommodityTrxDetailServiceImpl implements CommodityTrxDetailService {

	private final CommodityTrxDetailRepository repo;
	private final CommodityTrxSubdetailService commodityTrxSubdetailService;
	private final SupplierService supplierService;
	private final CommodityTrxService commodityTrxService;
	
	public CommodityTrxDetailServiceImpl(CommodityTrxDetailRepository repo,
			CommodityTrxSubdetailService commodityTrxSubdetailService, SupplierService supplierService,
			@Lazy CommodityTrxService commodityTrxService) {
		this.repo = repo;
		this.commodityTrxSubdetailService = commodityTrxSubdetailService;
		this.supplierService = supplierService;
		this.commodityTrxService = commodityTrxService;
	}

	@Override
	public void validateIdExist(String id) {
		if (!repo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity trx detail id is not found ");
		}
	}

	@Override
	public void validateBkNotExist(String commodityTrxId, String supplierId) {
		if (repo.existsByCommodityTrxIdAndSupplierId(commodityTrxId, supplierId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity trx detail with same code is exists ");
		}
	}

	@Override
	public void validateVersion(String id, Long version) {
		CommodityTrxDetail commodityTrxDetail = getEntityById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity trx is not active"));
		if (!commodityTrxDetail.getVersion().equals(version)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity trx detail version does not matched");
		}
	}

	@Override
	public List<CommodityTrxDetailResponse> getAllByHeaderId(String headerId) {
		List<CommodityTrxDetail> commodityTrxDetails = repo.findAllByCommodityTrxId(headerId);
		List<CommodityTrxDetailResponse> commodityTrxDetailResponses = commodityTrxDetails.stream().map(this::mapToResponse).toList();
		return commodityTrxDetailResponses;
	}

	@Override
	public Optional<CommodityTrxDetail> getEntityById(String id) {
		return repo.findById(id);
	}

	@Override
	public CommodityTrxDetail getValidatedEntityById(String id) {
		return getEntityById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"commodity trx detail is not exists"));
	}

	@Override
	public CommodityTrxDetailResponse getById(String id) {
		CommodityTrxDetail commodityTrx = getValidatedEntityById(id);
		return mapToResponse(commodityTrx);
	}

	@Override
	@Transactional
	public void add(CreateCommodityTrxDetailRequest data) {
		validateBkNotExist(data.getCommodityTrxId(), data.getSupplierId());

		CommodityTrxDetail commodityTrxDetail = new CommodityTrxDetail();
		BeanUtils.copyProperties(data, commodityTrxDetail);

		if(data.getCommodityTrxId() != null) {
			CommodityTrx commodityTrx = getExistCommodityTrx(data.getCommodityTrxId());
			commodityTrxDetail.setCommodityTrx(commodityTrx);
		}
		
		if(data.getSupplierId() != null) {
			Supplier supplier = getActiveSupplier(data.getSupplierId());
			commodityTrxDetail.setSupplier(supplier);
		}

		repo.save(commodityTrxDetail);
		
		for(CreateCommodityTrxSubdetailRequest detail : data.getSubdetails()) {
			detail.setCommodityTrxDetailId(commodityTrxDetail.getId());
			commodityTrxSubdetailService.add(detail);
		}
	}

	@Override
	@Transactional
	public void edit(UpdateCommodityTrxDetailRequest data) {
		validateIdExist(data.getId());
		CommodityTrxDetail commodityTrxDetail = getValidatedEntityById(data.getId());
		validateVersion(commodityTrxDetail.getId(), data.getVersion());
		BeanUtils.copyProperties(data, commodityTrxDetail);
		
		if(!data.getSupplierId().equals(commodityTrxDetail.getSupplier().getId())) {
			Supplier supplier = getActiveSupplier(data.getSupplierId());
			commodityTrxDetail.setSupplier(supplier);
			List<CommodityTrxSubdetailResponse> commodityTrxSubdetails = commodityTrxSubdetailService.getAllByDetailId(commodityTrxDetail.getId());
			for(CommodityTrxSubdetailResponse subdetail : commodityTrxSubdetails) {
				commodityTrxSubdetailService.delete(subdetail.getId());
			}
			
			for(CreateCommodityTrxSubdetailRequest subdetail : data.getSubdetails()) {
				subdetail.setCommodityTrxDetailId(commodityTrxDetail.getId());
				commodityTrxSubdetailService.add(subdetail);
			}
		}
		
		repo.saveAndFlush(commodityTrxDetail);
	}

	@Override
	@Transactional
	public void delete(String id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional
	public void delete(List<String> ids) {
		for (String id : ids) {
			delete(id);
		}
	}
	
	private CommodityTrx getExistCommodityTrx(String commodityTrxId) {
		return commodityTrxService.getEntityById(commodityTrxId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"commodity trx is not exists"));
	}
	
	private Supplier getExistSupplier(String supplierId) {
		return supplierService.getEntityById(supplierId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"supplier is not exists"));
	}
	
	private Supplier getActiveSupplier(String supplierId) {
		Supplier supplier = getExistSupplier(supplierId);
		if(Boolean.FALSE.equals(supplier.getIsActive())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "supplier is not active");
		}
		return supplier;
	}

	private CommodityTrxDetailResponse mapToResponse(CommodityTrxDetail commodityTrxDetail) {
		CommodityTrxDetailResponse commodityResponse = new CommodityTrxDetailResponse();
		BeanUtils.copyProperties(commodityTrxDetail, commodityResponse);
		
		CommodityTrx commodityTrx = commodityTrxDetail.getCommodityTrx();
		commodityResponse.setCommodityTrxId(commodityTrx.getId());
		
		Supplier supplier = commodityTrxDetail.getSupplier();
		commodityResponse.setSupplierId(supplier.getId());
		commodityResponse.setSupplierCode(supplier.getCode());
		commodityResponse.setSupplierName(supplier.getName());
		
		List<CommodityTrxSubdetailResponse> subdetailResponses = commodityTrxSubdetailService.getAllByDetailId(commodityTrxDetail.getId());
		commodityResponse.setSubdetails(subdetailResponses);
		
		return commodityResponse;
	}
}
