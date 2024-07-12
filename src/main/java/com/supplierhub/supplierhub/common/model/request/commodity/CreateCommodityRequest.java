package com.supplierhub.supplierhub.common.model.request.commodity;

public class CreateCommodityRequest {

	private String code;

	private String name;

	private String description;

	private Integer restockWhen;

	private Integer quantity;

	private String itemCategoryId;

	private Boolean isActive;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRestockWhen() {
		return restockWhen;
	}

	public void setRestockWhen(Integer restockWhen) {
		this.restockWhen = restockWhen;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getItemCategoryId() {
		return itemCategoryId;
	}

	public void setItemCategoryId(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
