package com.supplierhub.supplierhub.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.supplierhub.supplierhub.common.model.request.commoditytrx.CreateCommodityTrxDetailRequest;
import com.supplierhub.supplierhub.common.model.request.commoditytrx.CreateCommodityTrxRequest;
import com.supplierhub.supplierhub.common.model.request.commoditytrx.UpdateCommodityTrxRequest;
import com.supplierhub.supplierhub.common.model.response.CommodityTrxDetailResponse;
import com.supplierhub.supplierhub.common.model.response.CommodityTrxResponse;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrx;
import com.supplierhub.supplierhub.persistence.entity.User;
import com.supplierhub.supplierhub.persistence.repository.CommodityTrxRepository;
import com.supplierhub.supplierhub.service.CommodityTrxDetailService;
import com.supplierhub.supplierhub.service.CommodityTrxService;
import com.supplierhub.supplierhub.service.UserService;

@Service
public class CommodityTrxServiceImpl implements CommodityTrxService {

	private final CommodityTrxRepository repo;
	private final CommodityTrxDetailService commodityTrxDetailService;
	private final UserService userService;
	
	public CommodityTrxServiceImpl(CommodityTrxRepository repo, CommodityTrxDetailService commodityTrxDetailService,
			UserService userService) {
		this.repo = repo;
		this.commodityTrxDetailService = commodityTrxDetailService;
		this.userService = userService;
	}

	@Override
	public void validateIdExist(String id) {
		if (!repo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity trx id is not found ");
		}
	}

	@Override
	public void validateBkNotExist(String trxNumber, LocalDate trxDate) {
		if (repo.existsByTrxNumberAndTrxDate(trxNumber, trxDate)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity trx with same code is exists ");
		}
	}

	@Override
	public void validateVersion(String id, Long version) {
		CommodityTrx commodityTrx = getEntityById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity trx is not active"));
		if (!commodityTrx.getVersion().equals(version)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity trx version does not matched");
		}
	}

	@Override
	public List<CommodityTrxResponse> getAll() {
		List<CommodityTrx> commodityTrxs = repo.findAll();
		List<CommodityTrxResponse> commodityTrxResponse = commodityTrxs.stream().map(this::mapToResponse).toList();
		return commodityTrxResponse;
	}

	@Override
	public Optional<CommodityTrx> getEntityById(String id) {
		return repo.findById(id);
	}

	@Override
	public CommodityTrx getValidatedEntityById(String id) {
		return getEntityById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"commodity trx is not exists"));
	}

	@Override
	public CommodityTrxResponse getById(String id) {
		CommodityTrx commodityTrx = getValidatedEntityById(id);
		return mapToResponse(commodityTrx);
	}

	@Override
	@Transactional
	public void add(CreateCommodityTrxRequest data) {
		LocalDate now = LocalDate.now();
		
		validateBkNotExist(data.getTrxNumber(), now);

		CommodityTrx commodityTrx = new CommodityTrx();
		BeanUtils.copyProperties(data, commodityTrx);

		String trxNumber = data.getTrxNumber();
		
		if(data.getTrxNumber() == null) {
			Long countNumber = repo.getCount() + 1;
			trxNumber = countNumber.toString();
		}
		
		commodityTrx.setTrxNumber(trxNumber);
		commodityTrx.setTrxDate(now);
		
		User user = getActiveUser(data.getUserId());
		commodityTrx.setUser(user);

		repo.save(commodityTrx);
		
		for(CreateCommodityTrxDetailRequest detail : data.getDetails()) {
			detail.setCommodityTrxId(commodityTrx.getId());
			commodityTrxDetailService.add(detail);
		}
	}

	@Override
	@Transactional
	public void edit(UpdateCommodityTrxRequest data) {
		validateIdExist(data.getId());
		CommodityTrx commodityTrx = getValidatedEntityById(data.getId());
		validateVersion(commodityTrx.getId(), data.getVersion());
		BeanUtils.copyProperties(data, commodityTrx);
		
		if(data.getUserId() != null) {
			User user = getActiveUser(data.getUserId());
			commodityTrx.setUser(user);
		}
		
		repo.saveAndFlush(commodityTrx);
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
	
	private User getExistUser(String userId) {
		return userService.getEntityById(userId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"user is not exists"));
	}
	
	private User getActiveUser(String userId) {
		User user = getExistUser(userId);
		if(Boolean.FALSE.equals(user.getIsActive())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not active");
		}
		return user;
	}

	private CommodityTrxResponse mapToResponse(CommodityTrx commodityTrx) {
		CommodityTrxResponse commodityResponse = new CommodityTrxResponse();
		BeanUtils.copyProperties(commodityTrx, commodityResponse);

		User user = commodityTrx.getUser();
		commodityResponse.setUserId(user.getId());
		commodityResponse.setUserName(user.getFullName());
		
		List<CommodityTrxDetailResponse> details = commodityTrxDetailService.getAllByHeaderId(commodityTrx.getId());
		commodityResponse.setDetails(details);
		
		return commodityResponse;
	}
}
