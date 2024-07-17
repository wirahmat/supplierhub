package com.supplierhub.supplierhub.persistence.dao.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.dao.SupplierDao;
import com.supplierhub.supplierhub.persistence.entity.Supplier;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class SupplierDaoImpl implements SupplierDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean existsById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM suppliers s WHERE s.id = :id)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public boolean existsByCode(String code) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM suppliers s WHERE s.code = :code)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("code", code);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public List<Supplier> getAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM suppliers");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		List<Object[]> result = query.getResultList();

		return result.stream().map(this::mapObjectArrToEntity).toList();
	}

	@Override
	public Optional<Supplier> findById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM suppliers s WHERE s.id = :id");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		
		Object result = query.getSingleResult();

		Object[] resArr = (Object[]) result;
		
		Supplier supplier = mapObjectArrToEntity(resArr);
		
		return Optional.of(supplier);
	}

	@Override
	public Optional<Supplier> findByCode(String code) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM suppliers s WHERE s.code = :code");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("code", code);
		
		Object result = query.getSingleResult();

		Object[] resArr = (Object[]) result;
		
		Supplier supplier = mapObjectArrToEntity(resArr);
		
		return Optional.of(supplier);
	}

	@Override
	public Long getCount() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(sup.id) FROM suppliers sup ");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		Object result = query.getSingleResult();

		return Long.valueOf(result.toString());
	}

	private Supplier mapObjectArrToEntity(Object[] objArr) {
		Supplier supplier = new Supplier();
		supplier.setId(objArr[0].toString());
		supplier.setCode(objArr[1].toString());
		supplier.setName(objArr[2].toString());
		
		if(objArr[3] != null) {
			supplier.setDescription(objArr[3].toString());			
		}else {
			supplier.setDescription(null);
		}
		
		if(objArr[4] != null) {
			supplier.setAddress(objArr[4].toString());			
		}else {
			supplier.setDescription(null);
		}
		
		supplier.setCreatedAt(ZonedDateTime.parse(objArr[5].toString()));
		
		if(objArr[6] != null) {
			supplier.setUpdatedAt(ZonedDateTime.parse(objArr[6].toString()));			
		}else {
			supplier.setUpdatedAt(null);
		}
		
		supplier.setVersion(Long.valueOf(objArr[7].toString()));
		supplier.setIsActive(Boolean.valueOf(objArr[8].toString()));
		
		if(objArr[9] != null) {
			supplier.setDeletedAt(ZonedDateTime.parse(objArr[9].toString()));			
		}else {
			supplier.setDeletedAt(null);			
		}
		
		return supplier;
	}
	
}
