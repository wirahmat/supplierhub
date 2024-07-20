package com.supplierhub.supplierhub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.supplierhub.supplierhub.common.model.request.supplier.CreateSupplierDetailRequest;
import com.supplierhub.supplierhub.common.model.request.supplier.UpdateSupplierDetailRequest;
import com.supplierhub.supplierhub.common.model.response.SupplierDetailResponse;
import com.supplierhub.supplierhub.persistence.dao.SupplierDetailDao;
import com.supplierhub.supplierhub.persistence.entity.Commodity;
import com.supplierhub.supplierhub.persistence.entity.Supplier;
import com.supplierhub.supplierhub.persistence.entity.SupplierDetail;
import com.supplierhub.supplierhub.service.CommodityService;
import com.supplierhub.supplierhub.service.SupplierDetailService;
import com.supplierhub.supplierhub.service.SupplierService;

@Service
public class SupplierDetailServiceImpl implements SupplierDetailService {

	private final SupplierDetailDao dao;
	private final SupplierService supplierService;
	private final CommodityService commodityService;

	public SupplierDetailServiceImpl(SupplierDetailDao dao,
			@Lazy SupplierService supplierService, CommodityService commodityService) {
		this.dao = dao;
		this.supplierService = supplierService;
		this.commodityService = commodityService;
	}

	@Override
	public void validateIdExist(String id) {
		if (!dao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "supplier detail id is not found ");
		}
	}

	@Override
	public void validateBkNotExist(String supplierId, String commodityId) {
		if (dao.existsBySupplierIdAndCommodityId(supplierId, commodityId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "supplier detail with same code is exists ");
		}
	}

	@Override
	public void validateVersion(String id, Long version) {
		SupplierDetail supplier = getEntityById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "supplier detail is not active"));
		if (!supplier.getVersion().equals(version)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "supplier version does not matched");
		}
	}

	@Override
	public List<SupplierDetailResponse> getAllBySupplier(String supplierId) {
		List<SupplierDetail> suppliers = dao.findAllBySupplierId(supplierId);
		List<SupplierDetailResponse> supplierResponses = suppliers.stream().map(this::mapToResponse).toList();
		return supplierResponses;
	}

	@Override
	public List<SupplierDetailResponse> getAllByCommodity(String commodityId) {
		List<SupplierDetail> suppliers = dao.findAllByCommodityId(commodityId);
		List<SupplierDetailResponse> supplierResponses = suppliers.stream().map(this::mapToResponse).toList();
		return supplierResponses;
	}

	@Override
	public Optional<SupplierDetail> getEntityById(String id) {
		return dao.findById(id);
	}

	@Override
	public SupplierDetail getValidatedEntityById(String id) {
		return getEntityById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "supplier detail is not exists"));
	}

	@Override
	public SupplierDetailResponse getById(String id) {
		SupplierDetail commodity = getValidatedEntityById(id);
		return mapToResponse(commodity);
	}
	
	@Override
	public SupplierDetail getBySupplierAndCommodity(String supplierId, String commodityId) {
		return dao.findBySupplierIdAndCommodityId(supplierId, commodityId);
	}


	@Override
	@Transactional
	public void add(CreateSupplierDetailRequest data) {
		validateBkNotExist(data.getSupplierId(), data.getCommodityId());

		SupplierDetail supplierDetail = new SupplierDetail();
		BeanUtils.copyProperties(data, supplierDetail);

		Supplier supplier = getActiveSupplier(data.getSupplierId());
		supplierDetail.setSupplier(supplier);
		
		Commodity commodity = getActiveCommodity(data.getCommodityId());
		supplierDetail.setCommodity(commodity);
		
		dao.save(supplierDetail);
	}

	@Override
	@Transactional
	public void edit(UpdateSupplierDetailRequest data) {
		validateBkNotExist(data.getSupplierId(), data.getCommodityId());

		SupplierDetail supplierDetail = new SupplierDetail();
		BeanUtils.copyProperties(data, supplierDetail);

		if(data.getSupplierId() != null) {
			Supplier supplier = getActiveSupplier(data.getSupplierId());
			supplierDetail.setSupplier(supplier);			
		}
		
		if(data.getCommodityId() != null) {
			Commodity commodity = getActiveCommodity(data.getCommodityId());
			supplierDetail.setCommodity(commodity);			
		}
		
		dao.save(supplierDetail);
	}

	@Override
	@Transactional
	public void delete(String id) {
		SupplierDetail supplierDetail = getValidatedEntityById(id);
		dao.delete(supplierDetail);
	}

	@Override
	@Transactional
	public void delete(List<String> ids) {
		for (String id : ids) {
			delete(id);
		}
	}
	
	private Commodity getExistCommodity(String commodityId) {
		return commodityService.getEntityById(commodityId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"commodity is not exists"));
	}
	
	private Commodity getActiveCommodity(String commodityId) {
		Commodity commodity = getExistCommodity(commodityId);
		if(Boolean.FALSE.equals(commodity.getIsActive())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity is not active");
		}
		return commodity;
	}
	
	private Supplier getExistSupplier(String supplierId) {
		return supplierService.getEntityById(supplierId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"supplier is not exists"));
	}
	
	private Supplier getActiveSupplier(String supplierId) {
		Supplier supplier = getExistSupplier(supplierId);
		if(Boolean.FALSE.equals(supplier.getIsActive())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity is not active");
		}
		return supplier;
	}


	private SupplierDetailResponse mapToResponse(SupplierDetail supplierDetail) {
		SupplierDetailResponse supplierDetailResponse = new SupplierDetailResponse();
		BeanUtils.copyProperties(supplierDetail, supplierDetailResponse);

		Supplier supplier = supplierDetail.getSupplier();
		supplierDetailResponse.setSupplierId(supplier.getId());
		supplierDetailResponse.setSupplierCode(supplier.getCode());
		supplierDetailResponse.setSupplierName(supplier.getName());

		Commodity commodity = supplierDetail.getCommodity();
		supplierDetailResponse.setCommodityId(commodity.getId());
		supplierDetailResponse.setCommodityCode(commodity.getCode());
		supplierDetailResponse.setCommodityName(commodity.getName());

		return supplierDetailResponse;
	}

	
}
