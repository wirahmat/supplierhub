package com.supplierhub.supplierhub.service;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.common.model.request.supplier.CreateSupplierDetailRequest;
import com.supplierhub.supplierhub.common.model.request.supplier.UpdateSupplierDetailRequest;
import com.supplierhub.supplierhub.common.model.response.SupplierDetailResponse;
import com.supplierhub.supplierhub.persistence.entity.SupplierDetail;

public interface SupplierDetailService {

	List<SupplierDetailResponse> getAllBySupplier(String supplierId);
	
	List<SupplierDetailResponse> getAllByCommodity(String commodityId);

	Optional<SupplierDetail> getEntityById(String id);

	SupplierDetail getValidatedEntityById(String id);
	
	SupplierDetailResponse getById(String id);
	
	SupplierDetail getBySupplierAndCommodity(String supplierId, String commodityId);
	
	void add(CreateSupplierDetailRequest data);
	
	void edit(UpdateSupplierDetailRequest data);
	
	void delete(String id);
	
	void delete(List<String> ids);
}
