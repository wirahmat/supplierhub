package com.supplierhub.supplierhub.common.model.response;

import java.util.List;

public class CommodityTrxDetailResponse {

	private String commodityTrxId;
	private String supplierId;
	private String supplierCode;
	private String supplierName;
	private List<CommodityTrxSubdetailResponse> subdetails;

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

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public List<CommodityTrxSubdetailResponse> getSubdetails() {
		return subdetails;
	}

	public void setSubdetails(List<CommodityTrxSubdetailResponse> subdetails) {
		this.subdetails = subdetails;
	}

}
