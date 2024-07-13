package com.supplierhub.supplierhub.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String>, JpaSpecificationExecutor<Supplier> {

	boolean existsByCode(String code);

	Supplier findByCode(String code);

	@Query(value = "SELECT COUNT(sup.id) FROM Supplier sup ")
	Long getCount();

}
