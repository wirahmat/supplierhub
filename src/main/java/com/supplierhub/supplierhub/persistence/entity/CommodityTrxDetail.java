package com.supplierhub.supplierhub.persistence.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "commodity_trx_details", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "commodity_trx_id", "supplier_id" }) })
@SQLDelete(sql = "UPDATE commodity_trx_details SET deleted_at = now() WHERE id=? AND version =?")
@SQLRestriction("deleted_at IS NULL")
public class CommodityTrxDetail extends MasterEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "commodity_trx_id", nullable = false)
	private CommodityTrx commodityTrx;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplier_id", nullable = false)
	private Supplier supplier;

	public CommodityTrx getCommodityTrx() {
		return commodityTrx;
	}

	public void setCommodityTrx(CommodityTrx commodityTrx) {
		this.commodityTrx = commodityTrx;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
