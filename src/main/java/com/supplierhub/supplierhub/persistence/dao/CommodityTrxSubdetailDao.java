package com.supplierhub.supplierhub.persistence.dao;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.persistence.entity.CommodityTrxSubdetail;

public interface CommodityTrxSubdetailDao {

	boolean existsById(String id);

	boolean existsByCommodityTrxDetailIdAndCommodityId(String commodityTrxDetailId, String commodityId);

	List<CommodityTrxSubdetail> getAllByDetailId(String detailId);

	Optional<CommodityTrxSubdetail> findById(String id);
	
	Long getCount();
}
