package com.supplierhub.supplierhub.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {

	boolean existsByCode(String code);
	
	Category findByCode(String code);
	
	@Query(value = "SELECT COUNT(cat.id) FROM Category cat ")
	Long getCount();
}
