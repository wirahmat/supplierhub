package com.supplierhub.supplierhub.persistence.entity;

import java.time.LocalDate;

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
@Table(name = "commodity_trx", uniqueConstraints = { @UniqueConstraint(columnNames = { "trx_number", "trx_date" }) })
@SQLDelete(sql = "UPDATE commodity_trx SET deleted_at = now() WHERE id=? AND version =?")
@SQLRestriction("deleted_at IS NULL")
public class CommodityTrx extends MasterEntity{

	@Column(name = "trx_number", nullable = false)
	private String trxNumber;

	@Column(name = "trx_date", nullable = false)
	private LocalDate trxDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
