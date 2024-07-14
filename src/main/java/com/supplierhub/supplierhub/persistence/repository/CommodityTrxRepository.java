package com.supplierhub.supplierhub.persistence.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.entity.CommodityTrx;

@Repository
public interface CommodityTrxRepository
		extends JpaRepository<CommodityTrx, String>, JpaSpecificationExecutor<CommodityTrx> {
	
	boolean existsByTrxNumberAndTrxDate(String trxNumber, LocalDate trxDate);
	
	@Query(value = "SELECT COUNT(com.id) FROM CommodityTrx com ")
	Long getCount();
}
