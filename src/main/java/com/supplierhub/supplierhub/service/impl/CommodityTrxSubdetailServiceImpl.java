package com.supplierhub.supplierhub.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.supplierhub.supplierhub.common.model.request.commodity.UpdateCommodityRequest;
import com.supplierhub.supplierhub.common.model.request.commoditytrx.CreateCommodityTrxSubdetailRequest;
import com.supplierhub.supplierhub.common.model.request.commoditytrx.UpdateCommodityTrxSubdetailRequest;
import com.supplierhub.supplierhub.common.model.response.CommodityTrxSubdetailResponse;
import com.supplierhub.supplierhub.persistence.dao.CommodityTrxSubdetailDao;
import com.supplierhub.supplierhub.persistence.entity.Commodity;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrxDetail;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrxSubdetail;
import com.supplierhub.supplierhub.persistence.entity.SupplierDetail;
import com.supplierhub.supplierhub.persistence.repository.CommodityTrxSubdetailRepository;
import com.supplierhub.supplierhub.service.CommodityService;
import com.supplierhub.supplierhub.service.CommodityTrxDetailService;
import com.supplierhub.supplierhub.service.CommodityTrxSubdetailService;
import com.supplierhub.supplierhub.service.SupplierDetailService;

@Service
public class CommodityTrxSubdetailServiceImpl implements CommodityTrxSubdetailService{

	private final CommodityTrxSubdetailRepository repo;
	private final CommodityTrxSubdetailDao dao;
	private final CommodityTrxDetailService commodityTrxDetailService;
	private final CommodityService commodityService;
	private final SupplierDetailService supplierDetailService;

	public CommodityTrxSubdetailServiceImpl(CommodityTrxSubdetailRepository repo, CommodityTrxSubdetailDao dao,
			@Lazy CommodityTrxDetailService commodityTrxDetailService, CommodityService commodityService,
			SupplierDetailService supplierDetailService) {
		this.repo = repo;
		this.dao = dao;
		this.commodityTrxDetailService = commodityTrxDetailService;
		this.commodityService = commodityService;
		this.supplierDetailService = supplierDetailService;
	}

	@Override
	public void validateIdExist(String id) {
		if (!dao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity trx subdetail id is not found ");
		}
	}

	@Override
	public void validateBkNotExist(String commodityTrxDetailId, String commodityId) {
		if (dao.existsByCommodityTrxDetailIdAndCommodityId(commodityTrxDetailId, commodityId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity trx subdetail with same code is exists ");
		}
	}

	@Override
	public void validateVersion(String id, Long version) {
		CommodityTrxSubdetail commodityTrxSubdetail= getEntityById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity trx subdetail is not active"));
		if (!commodityTrxSubdetail.getVersion().equals(version)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity trx subdetail version does not matched");
		}
	}

	@Override
	public List<CommodityTrxSubdetailResponse> getAllByDetailId(String detailId) {
		List<CommodityTrxSubdetail> commodityTrxDetails = dao.getAllByDetailId(detailId);
		List<CommodityTrxSubdetailResponse> commodityTrxDetailResponses = commodityTrxDetails.stream().map(this::mapToResponse).toList();
		return commodityTrxDetailResponses;
	}

	@Override
	public Optional<CommodityTrxSubdetail> getEntityById(String id) {
		return dao.findById(id);
	}

	@Override
	public CommodityTrxSubdetail getValidatedEntityById(String id) {
		return getEntityById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"commodity trx detail is not exists"));
	}

	@Override
	public CommodityTrxSubdetailResponse getById(String id) {
		CommodityTrxSubdetail commodityTrx = getValidatedEntityById(id);
		return mapToResponse(commodityTrx);
	}

	@Override
	@Transactional
	public void add(CreateCommodityTrxSubdetailRequest data, String supplierId) {
		validateBkNotExist(data.getCommodityTrxDetailId(), data.getCommodityId());

		CommodityTrxSubdetail commodityTrxSubdetail = new CommodityTrxSubdetail();
		BeanUtils.copyProperties(data, commodityTrxSubdetail);

		if(data.getCommodityTrxDetailId() != null) {
			CommodityTrxDetail commodityTrxDetail = getExistCommodityTrxDetail(data.getCommodityTrxDetailId());
			commodityTrxSubdetail.setCommodityTrxDetail(commodityTrxDetail);
		}
		
		Commodity commodity = new Commodity();
		if(data.getCommodityId() != null) {
			commodity = getActiveCommodity(data.getCommodityId());
			commodityTrxSubdetail.setCommodity(commodity);
		}
		
		BigDecimal totalAmount = BigDecimal.ZERO;
		if(supplierId != null) {
			SupplierDetail supplierDetail = supplierDetailService.getBySupplierAndCommodity(supplierId, data.getCommodityId());
			totalAmount = supplierDetail.getAmount().multiply(BigDecimal.valueOf(data.getQuantity()));
		}else {
			Optional<CommodityTrxDetail> commodityTrxDetailOpt = commodityTrxDetailService.getEntityById(data.getCommodityTrxDetailId());
			if(commodityTrxDetailOpt.isPresent()) {
				CommodityTrxDetail commodityTrxDetail = commodityTrxDetailOpt.get();
				SupplierDetail supplierDetail = supplierDetailService.getBySupplierAndCommodity(commodityTrxDetail.getSupplier().getId(), data.getCommodityId());
				totalAmount = supplierDetail.getAmount().multiply(BigDecimal.valueOf(data.getQuantity()));
			}
		}
		
		commodityTrxSubdetail.setTotalAmount(totalAmount);
		
		repo.save(commodityTrxSubdetail);
		
		UpdateCommodityRequest updateCommodityRequest = new UpdateCommodityRequest();
		updateCommodityRequest.setId(commodity.getId());
		updateCommodityRequest.setVersion(commodity.getVersion());
		updateCommodityRequest.setQuantity(commodity.getQuantity() + data.getQuantity());
		
		commodityService.edit(updateCommodityRequest);
	}

	@Override
	@Transactional
	public void edit(UpdateCommodityTrxSubdetailRequest data) {
		validateIdExist(data.getId());
		CommodityTrxSubdetail commodityTrxSubdetail = getValidatedEntityById(data.getId());
		validateVersion(commodityTrxSubdetail.getId(), data.getVersion());
		BeanUtils.copyProperties(data, commodityTrxSubdetail);
		
		if(data.getCommodityId() != null) {
			Commodity commodity = getExistCommodity(data.getCommodityId());
			commodityTrxSubdetail.setCommodity(commodity);
		}
		
		repo.saveAndFlush(commodityTrxSubdetail);
	}

	@Override
	@Transactional
	public void delete(String id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional
	public void delete(List<String> ids) {
		for (String id : ids) {
			delete(id);
		}
	}
	
	private CommodityTrxDetail getExistCommodityTrxDetail(String commodityTrxDetailId) {
		return commodityTrxDetailService.getEntityById(commodityTrxDetailId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"commodity trx detail is not exists"));
	}
	
	private Commodity getExistCommodity(String commodityId) {
		return commodityService.getEntityById(commodityId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"commodity is not exists"));
	}
	
	private Commodity getActiveCommodity(String commodityId) {
		Commodity commodity = getExistCommodity(commodityId);
		if(Boolean.FALSE.equals(commodity.getIsActive())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "supplier is not active");
		}
		return commodity;
	}

	private CommodityTrxSubdetailResponse mapToResponse(CommodityTrxSubdetail commodityTrxSubdetail) {
		CommodityTrxSubdetailResponse commodityResponse = new CommodityTrxSubdetailResponse();
		BeanUtils.copyProperties(commodityTrxSubdetail, commodityResponse);
		
		CommodityTrxDetail commodityTrxDetail = commodityTrxSubdetail.getCommodityTrxDetail();
		commodityResponse.setCommodityTrxDetailId(commodityTrxDetail.getId());
		
		Commodity commodity = commodityTrxSubdetail.getCommodity();
		commodityResponse.setCommodityId(commodity.getId());
		commodityResponse.setCommodityCode(commodity.getCode());
		commodityResponse.setCommodityName(commodity.getName());
		
		return commodityResponse;
	}
}
