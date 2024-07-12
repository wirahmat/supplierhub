package com.supplierhub.supplierhub.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.entity.Commodity;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, String>, JpaSpecificationExecutor<Commodity> {

	boolean existsByCode(String code);

	Commodity findByCode(String code);

	@Query(value = "SELECT COUNT(com.id) FROM Commodity com ")
	Long getCount();
}
