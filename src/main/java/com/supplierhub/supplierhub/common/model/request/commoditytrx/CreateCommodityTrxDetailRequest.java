package com.supplierhub.supplierhub.common.model.request.commoditytrx;

import java.util.List;

public class CreateCommodityTrxDetailRequest {

	private String commodityTrxId;
	private String supplierId;
	private List<CreateCommodityTrxSubdetailRequest> subdetails;

	public String getCommodityTrxId() {
		return commodityTrxId;
	}

	public void setCommodityTrxId(String commodityTrxId) {
		this.commodityTrxId = commodityTrxId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public List<CreateCommodityTrxSubdetailRequest> getSubdetails() {
		return subdetails;
	}

	public void setSubdetails(List<CreateCommodityTrxSubdetailRequest> subdetails) {
		this.subdetails = subdetails;
	}

}
