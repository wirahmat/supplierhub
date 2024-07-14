package com.supplierhub.supplierhub.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.entity.CommodityTrxSubdetail;

@Repository
public interface CommodityTrxSubdetailRepository
		extends JpaRepository<CommodityTrxSubdetail, String>, JpaSpecificationExecutor<CommodityTrxSubdetail> {

	boolean existsByCommodityTrxDetailIdAndCommodityId(String commodityTrxDetailId, String commodityId);

	@Query(value = "SELECT COUNT(com.id) FROM CommodityTrxSubdetail com ")
	Long getCount();
	
	List<CommodityTrxSubdetail> findAllByCommodityTrxDetailId(String detailId);
}
