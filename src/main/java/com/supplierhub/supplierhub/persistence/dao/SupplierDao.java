package com.supplierhub.supplierhub.persistence.dao;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.persistence.entity.Supplier;
import com.supplierhub.supplierhub.persistence.entity.Supplier;

public interface SupplierDao {

	boolean existsById(String id);

	boolean existsByCode(String code);

	List<Supplier> getAll();

	Optional<Supplier> findById(String id);
	
	Optional<Supplier> findByCode(String code);
	
	Long getCount();
	
	Supplier save(Supplier supplier);

	Supplier saveAndFlush(Supplier supplier);

	boolean delete(Supplier supplier);
}
