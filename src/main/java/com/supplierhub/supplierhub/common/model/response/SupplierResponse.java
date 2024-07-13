package com.supplierhub.supplierhub.common.model.response;

import java.util.List;

public class SupplierResponse extends MasterResponse {

	private String code;
	private String name;
	private String description;
	private String address;
	private List<SupplierDetailResponse> detailResponses;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<SupplierDetailResponse> getDetailResponses() {
		return detailResponses;
	}

	public void setDetailResponses(List<SupplierDetailResponse> detailResponses) {
		this.detailResponses = detailResponses;
	}

}
