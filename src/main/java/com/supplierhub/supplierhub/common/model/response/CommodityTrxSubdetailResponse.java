package com.supplierhub.supplierhub.common.model.response;

import java.math.BigDecimal;

public class CommodityTrxSubdetailResponse extends MasterResponse{

	private String commodityTrxDetailId;
	private String commodityId;
	private String commodityCode;
	private String commodityName;
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

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
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
