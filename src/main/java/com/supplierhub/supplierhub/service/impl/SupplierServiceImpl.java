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
import com.supplierhub.supplierhub.common.model.request.supplier.CreateSupplierRequest;
import com.supplierhub.supplierhub.common.model.request.supplier.UpdateSupplierRequest;
import com.supplierhub.supplierhub.common.model.response.SupplierDetailResponse;
import com.supplierhub.supplierhub.common.model.response.SupplierResponse;
import com.supplierhub.supplierhub.persistence.entity.Supplier;
import com.supplierhub.supplierhub.persistence.repository.SupplierRepository;
import com.supplierhub.supplierhub.service.SupplierDetailService;
import com.supplierhub.supplierhub.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService{

	private final SupplierRepository repo;
	private SupplierDetailService supplierDetailService;
	
	public SupplierServiceImpl(SupplierRepository repo, @Lazy SupplierDetailService supplierDetailService) {
		this.repo = repo;
		this.supplierDetailService = supplierDetailService;
	}

	@Override
	public void validateIdExist(String id) {
		if (!repo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"supplier id is not found ");
		}
	}

	@Override
	public void validateIdActive(String id) {
		Supplier supplier = getEntityById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"supplier id is not found "));
		if (Boolean.FALSE.equals(supplier.getIsActive())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"supplier is not active");
		}
	}
	
	@Override
	public void validateBkNotExist(String code) {
		if (repo.existsByCode(code)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"supplier with same code is exists ");
		}
	}

	@Override
	public void validateVersion(String id, Long version) {
		Supplier supplier = getEntityById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"supplier is not active" ));
		if (!supplier.getVersion().equals(version)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "supplier version does not matched");
		}
	}

	@Override
	public List<SupplierResponse> getAll() {
		List<Supplier> suppliers = repo.findAll();
		List<SupplierResponse> supplierResponses = suppliers.stream().map(this::mapToResponse).toList();
		return supplierResponses;
	}

	@Override
	public Optional<Supplier> getEntityById(String id) {
		return repo.findById(id);
	}

	@Override
	public Supplier getValidatedEntityById(String id) {
		return getEntityById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"supplier is not exists"));
	}

	@Override
	public SupplierResponse getById(String id) {
		Supplier supplier = getValidatedEntityById(id);
		return mapToResponse(supplier);
	}

	@Override
	@Transactional
	public void add(CreateSupplierRequest data) {
		validateBkNotExist(data.getCode());
		
		Supplier supplier = new Supplier();
		BeanUtils.copyProperties(data, supplier);
		
		if(!data.getDetails().isEmpty()) {
			for(CreateSupplierDetailRequest detailRequest : data.getDetails()) {
				supplierDetailService.add(detailRequest);
			}			
		}
		
		repo.save(supplier);
	}

	@Override
	@Transactional
	public void edit(UpdateSupplierRequest data) {
		validateIdExist(data.getId());
		Supplier supplier = getValidatedEntityById(data.getId());
		validateVersion(supplier.getId(), data.getVersion());
		BeanUtils.copyProperties(data, supplier);
		repo.saveAndFlush(supplier);
	}

	@Override
	@Transactional
	public void delete(String id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional
	public void delete(List<String> ids) {
		for(String id : ids) {
			delete(id);
		}
	}

	private SupplierResponse mapToResponse(Supplier supplier) {
		SupplierResponse supplierResponse = new SupplierResponse();
		BeanUtils.copyProperties(supplier, supplierResponse);
		
		List<SupplierDetailResponse> detailResponse = supplierDetailService.getAllBySupplier(supplier.getId());
		supplierResponse.setDetailResponses(detailResponse);
		
		return supplierResponse;
	}
	
}
