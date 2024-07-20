package com.supplierhub.supplierhub.persistence.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.persistence.entity.CommodityTrx;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrx;

public interface CommodityTrxDao {

	boolean existsById(String id);

	boolean existsByTrxNumberAndTrxDate(String trxNumber, LocalDate trxDate);

	List<CommodityTrx> getAll();

	Optional<CommodityTrx> findById(String id);
	
	Long getCount();
	
	CommodityTrx save(CommodityTrx commodityTrx);

	CommodityTrx saveAndFlush(CommodityTrx commodityTrx);

	boolean delete(CommodityTrx commodityTrx);
}
