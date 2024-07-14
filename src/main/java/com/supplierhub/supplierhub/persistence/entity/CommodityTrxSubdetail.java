package com.supplierhub.supplierhub.persistence.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "commodity_trx_subdetails", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "commodity_trx_detail_id", "commodity_id" }) })
@SQLDelete(sql = "UPDATE commodity_trx_subdetails SET deleted_at = now() WHERE id=? AND version =?")
@SQLRestriction("deleted_at IS NULL")
public class CommodityTrxSubdetail extends MasterEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "commodity_trx_detail_id", nullable = false)
	private CommodityTrxDetail commodityTrxDetail;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "commodity_id", nullable = false)
	private Commodity commodity;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "total_amount", nullable = false)
	private BigDecimal totalAmount;

	public CommodityTrxDetail getCommodityTrxDetail() {
		return commodityTrxDetail;
	}

	public void setCommodityTrxDetail(CommodityTrxDetail commodityTrxDetail) {
		this.commodityTrxDetail = commodityTrxDetail;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
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
