package com.supplierhub.supplierhub.service;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.common.model.request.supplier.CreateSupplierRequest;
import com.supplierhub.supplierhub.common.model.request.supplier.UpdateSupplierRequest;
import com.supplierhub.supplierhub.common.model.response.SupplierResponse;
import com.supplierhub.supplierhub.persistence.entity.Supplier;

public interface SupplierService {

	List<SupplierResponse> getAll();

	Optional<Supplier> getEntityById(String id);

	Supplier getValidatedEntityById(String id);

	SupplierResponse getById(String id);

	void add(CreateSupplierRequest data);

	void edit(UpdateSupplierRequest data);

	void delete(String id);

	void delete(List<String> ids);

}
