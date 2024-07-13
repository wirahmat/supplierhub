package com.supplierhub.supplierhub.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.supplierhub.supplierhub.persistence.entity.SupplierDetail;

public interface SupplierDetailRepository
		extends JpaRepository<SupplierDetail, String>, JpaSpecificationExecutor<SupplierDetail> {

	boolean existsBySupplierIdAndCommodityId(String supplierId, String commodityId);
	
	SupplierDetail findBySupplierIdAndCommodityId(String supplierId, String commodityId);
	
	List<SupplierDetail> findAllBySupplierId(String supplierId);
	
	List<SupplierDetail> findAllByCommodityId(String commodityId);
	
}
