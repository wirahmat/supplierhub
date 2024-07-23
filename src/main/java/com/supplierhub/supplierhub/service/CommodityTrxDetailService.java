package com.supplierhub.supplierhub.service;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.common.model.request.commoditytrx.CreateCommodityTrxDetailRequest;
import com.supplierhub.supplierhub.common.model.request.commoditytrx.UpdateCommodityTrxDetailRequest;
import com.supplierhub.supplierhub.common.model.response.CommodityTrxDetailResponse;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrxDetail;

public interface CommodityTrxDetailService {

	List<CommodityTrxDetailResponse> getAllByHeaderId(String headerId);

	Optional<CommodityTrxDetail> getEntityById(String id);

	CommodityTrxDetail getValidatedEntityById(String id);
	
	CommodityTrxDetailResponse getById(String id);
	
	void add(CreateCommodityTrxDetailRequest data);
	
	void edit(UpdateCommodityTrxDetailRequest data);
	
	void delete(String id);
	
	void delete(List<String> ids);
	
}
