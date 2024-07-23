package com.supplierhub.supplierhub.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.supplierhub.supplierhub.common.model.request.commodity.CreateCommodityRequest;
import com.supplierhub.supplierhub.common.model.request.commodity.UpdateCommodityRequest;
import com.supplierhub.supplierhub.common.model.response.CommodityResponse;
import com.supplierhub.supplierhub.persistence.dao.CommodityDao;
import com.supplierhub.supplierhub.persistence.entity.Category;
import com.supplierhub.supplierhub.persistence.entity.Commodity;
import com.supplierhub.supplierhub.service.CategoryService;
import com.supplierhub.supplierhub.service.CommodityService;

@Service
public class CommodityServiceImpl implements CommodityService {

	private final CategoryService categoryService;
	private final CommodityDao dao;

	public CommodityServiceImpl(CategoryService categoryService, CommodityDao dao) {
		this.categoryService = categoryService;
		this.dao = dao;
	}

	private void validateIdExist(String id) {
		if (!dao.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity id is not found ");
		}
	}

	private void validateBkNotExist(String code) {
		if (dao.existsByCode(code)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity with same code is exists ");
		}
	}

	private void validateVersion(String id, Long version) {
		Commodity commodity = getEntityById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity is not active"));
		if (!commodity.getVersion().equals(version)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity version does not matched");
		}
	}

	@Override
	public List<CommodityResponse> getAll() {
		List<Commodity> commodities = dao.getAll();
		List<CommodityResponse> commodityResponse = commodities.stream().map(this::mapToResponse).toList();
		return commodityResponse;
	}

	@Override
	public Optional<Commodity> getEntityById(String id) {
		return dao.findById(id);
	}

	@Override
	public Commodity getValidatedEntityById(String id) {
		return getEntityById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity is not exists"));
	}

	@Override
	public CommodityResponse getById(String id) {
		Commodity commodity = getValidatedEntityById(id);
		return mapToResponse(commodity);
	}

	@Override
	@Transactional
	public void add(CreateCommodityRequest data) {
		validateBkNotExist(data.getCode());

		Commodity commodity = new Commodity();
		BeanUtils.copyProperties(data, commodity);

		commodity.setRegisteredDate(LocalDate.now());

		Category category = getActiveCategory(data.getItemCategoryId());
		commodity.setCategory(category);

		if (data.getIsActive() == null) {
			commodity.setIsActive(true);
		}

		dao.save(commodity);
	}

	@Override
	@Transactional
	public void edit(UpdateCommodityRequest data) {
		validateIdExist(data.getId());
		Commodity commodity = getValidatedEntityById(data.getId());
		validateVersion(commodity.getId(), data.getVersion());
		BeanUtils.copyProperties(data, commodity);

		if (data.getItemCategoryId() != null && !data.getItemCategoryId().equals(commodity.getCategory().getId())) {
			Category category = getActiveCategory(data.getItemCategoryId());
			commodity.setCategory(category);
		}

		dao.saveAndFlush(commodity);
	}

	@Override
	@Transactional
	public void delete(String id) {
		validateIdExist(id);
		Commodity commodity = getValidatedEntityById(id);
		dao.delete(commodity);
	}

	@Override
	@Transactional
	public void delete(List<String> ids) {
		for (String id : ids) {
			delete(id);
		}
	}

	private Category getExistCategory(String categoryId) {
		return categoryService.getEntityById(categoryId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "category is not exists"));
	}

	private Category getActiveCategory(String categoryId) {
		Category category = getExistCategory(categoryId);
		if (Boolean.FALSE.equals(category.getIsActive())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "commodity is not active");
		}
		return category;
	}

	private CommodityResponse mapToResponse(Commodity commodity) {
		CommodityResponse commodityResponse = new CommodityResponse();
		BeanUtils.copyProperties(commodity, commodityResponse);

		Category category = commodity.getCategory();
		commodityResponse.setCategoryId(category.getId());
		commodityResponse.setCategoryCode(category.getCode());
		commodityResponse.setCategoryName(category.getName());

		return commodityResponse;
	}

}
