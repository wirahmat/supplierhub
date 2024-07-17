package com.supplierhub.supplierhub.persistence.dao;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.persistence.entity.CommodityTrxDetail;

public interface CommodityTrxDetailDao {

	boolean existsById(String id);

	boolean existsByCommodityTrxIdAndSupplierId(String commodityTrxId, String supplierId);

	List<CommodityTrxDetail> getAllByHeaderId(String headerId);

	Optional<CommodityTrxDetail> findById(String id);
	
	Long getCount();
	
}
