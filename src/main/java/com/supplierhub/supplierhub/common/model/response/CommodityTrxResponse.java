package com.supplierhub.supplierhub.common.model.response;

import java.time.LocalDate;
import java.util.List;

public class CommodityTrxResponse extends MasterResponse {

	private String trxNumber;
	private LocalDate trxDate;
	private String userId;
	private String userName;
	private List<CommodityTrxDetailResponse> details;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<CommodityTrxDetailResponse> getDetails() {
		return details;
	}

	public void setDetails(List<CommodityTrxDetailResponse> details) {
		this.details = details;
	}

}
