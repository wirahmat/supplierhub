package com.supplierhub.supplierhub.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.common.model.request.commoditytrx.CreateCommodityTrxRequest;
import com.supplierhub.supplierhub.common.model.request.commoditytrx.UpdateCommodityTrxRequest;
import com.supplierhub.supplierhub.common.model.response.CommodityTrxResponse;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrx;

public interface CommodityTrxService {

	void validateIdExist(String id);

	void validateBkNotExist(String trxNumber, LocalDate trxDate);

	void validateVersion(String id, Long version);

	List<CommodityTrxResponse> getAll();

	Optional<CommodityTrx> getEntityById(String id);

	CommodityTrx getValidatedEntityById(String id);
	
	CommodityTrxResponse getById(String id);
	
	void add(CreateCommodityTrxRequest data);
	
	void edit(UpdateCommodityTrxRequest data);
	
	void delete(String id);
	
	void delete(List<String> ids);
	
}
