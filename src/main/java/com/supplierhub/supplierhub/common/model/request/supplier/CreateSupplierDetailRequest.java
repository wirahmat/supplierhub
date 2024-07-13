package com.supplierhub.supplierhub.common.model.request.supplier;

import java.math.BigDecimal;

public class CreateSupplierDetailRequest {

	private String supplierId;

	private String commodityId;

	private BigDecimal amount;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
