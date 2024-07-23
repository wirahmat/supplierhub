package com.supplierhub.supplierhub.service;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.common.model.request.commoditytrx.CreateCommodityTrxSubdetailRequest;
import com.supplierhub.supplierhub.common.model.request.commoditytrx.UpdateCommodityTrxSubdetailRequest;
import com.supplierhub.supplierhub.common.model.response.CommodityTrxSubdetailResponse;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrxSubdetail;

public interface CommodityTrxSubdetailService {

	List<CommodityTrxSubdetailResponse> getAllByDetailId(String detailId);

	Optional<CommodityTrxSubdetail> getEntityById(String id);

	CommodityTrxSubdetail getValidatedEntityById(String id);
	
	CommodityTrxSubdetailResponse getById(String id);
	
	void add(CreateCommodityTrxSubdetailRequest data, String supplierId);
	
	void edit(UpdateCommodityTrxSubdetailRequest data);
	
	void delete(String id);
	
	void delete(List<String> ids);
}
