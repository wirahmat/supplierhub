package com.supplierhub.supplierhub.common.model.request.commoditytrx;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateCommodityTrxRequest {

	@NotBlank
	private String id;

	@NotNull
	private Long version;

	private String trxNumber;
	private String userId;

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

	public String getTrxNumber() {
		return trxNumber;
	}

	public void setTrxNumber(String trxNumber) {
		this.trxNumber = trxNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
