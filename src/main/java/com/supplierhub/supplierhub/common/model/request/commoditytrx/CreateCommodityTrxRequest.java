package com.supplierhub.supplierhub.common.model.request.commoditytrx;

import java.time.LocalDate;
import java.util.List;

public class CreateCommodityTrxRequest {

	private String trxNumber;
	private LocalDate trxDate;
	private String userId;
	private List<CreateCommodityTrxDetailRequest> details;

	public String getTrxNumber() {
		return trxNumber;
	}

	public void setTrxNumber(String trxNumber) {
		this.trxNumber = trxNumber;
	}

	public LocalDate getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(LocalDate trxDate) {
		this.trxDate = trxDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<CreateCommodityTrxDetailRequest> getDetails() {
		return details;
	}

	public void setDetails(List<CreateCommodityTrxDetailRequest> details) {
		this.details = details;
	}

}
