package com.supplierhub.supplierhub.common.model.request.commoditytrx;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateCommodityTrxDetailRequest {

	@NotBlank
	private String id;

	@NotNull
	private Long version;
	private String commodityTrxId;
	private String supplierId;
	private List<CreateCommodityTrxSubdetailRequest> subdetails;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

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
