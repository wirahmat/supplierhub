package com.supplierhub.supplierhub.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.entity.CommodityTrxDetail;

@Repository
public interface CommodityTrxDetailRepository
		extends JpaRepository<CommodityTrxDetail, String>, JpaSpecificationExecutor<CommodityTrxDetail> {

	boolean existsByCommodityTrxIdAndSupplierId(String commodityTrxId, String supplierId);

	@Query(value = "SELECT COUNT(com.id) FROM CommodityTrxDetail com ")
	Long getCount();
	
	List<CommodityTrxDetail> findAllByCommodityTrxId(String commodityTrxId);
}
