package com.supplierhub.supplierhub.persistence.dao;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.persistence.entity.SupplierDetail;

public interface SupplierDetailDao {

	boolean existsById(String id);

	boolean existsBySupplierIdAndCommodityId(String supplierId, String commodityId);

	List<SupplierDetail> findAllBySupplierId(String supplierId);
	
	List<SupplierDetail> findAllByCommodityId(String commodityId);

	SupplierDetail findBySupplierIdAndCommodityId(String supplierId, String commodityId);
	
	Optional<SupplierDetail> findById(String id);
	
}
