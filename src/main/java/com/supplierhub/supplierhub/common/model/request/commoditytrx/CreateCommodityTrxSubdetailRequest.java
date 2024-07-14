package com.supplierhub.supplierhub.common.model.request.commoditytrx;

import java.math.BigDecimal;

public class CreateCommodityTrxSubdetailRequest {

	private String commodityTrxDetailId;
	private String commodityId;
	private Integer quantity;
	private BigDecimal totalAmount;

	public String getCommodityTrxDetailId() {
		return commodityTrxDetailId;
	}

	public void setCommodityTrxDetailId(String commodityTrxDetailId) {
		this.commodityTrxDetailId = commodityTrxDetailId;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

}
