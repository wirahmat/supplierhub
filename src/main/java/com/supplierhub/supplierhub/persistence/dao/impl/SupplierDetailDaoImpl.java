package com.supplierhub.supplierhub.persistence.dao.impl;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.dao.SupplierDetailDao;
import com.supplierhub.supplierhub.persistence.entity.Commodity;
import com.supplierhub.supplierhub.persistence.entity.Supplier;
import com.supplierhub.supplierhub.persistence.entity.SupplierDetail;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class SupplierDetailDaoImpl implements SupplierDetailDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean existsById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM supplier_details sd WHERE sd.id = :id)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public boolean existsBySupplierIdAndCommodityId(String supplierId, String commodityId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM supplier_details sd WHERE sd.supplier_id = :supplierId AND sd.commodity_id = :commodityId)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("supplierId", supplierId);
		query.setParameter("commodityId", commodityId);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public List<SupplierDetail> findAllBySupplierId(String supplierId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM supplier_details sd WHERE sd.supplier_id = :supplierId");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("supplierId", supplierId);
		List<Object[]> result = query.getResultList();

		return result.stream().map(this::mapObjectArrToEntity).toList();
	}

	@Override
	public List<SupplierDetail> findAllByCommodityId(String commodityId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM supplier_details sd WHERE sd.commodity_id = :commodityId");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("commodityId", commodityId);
		List<Object[]> result = query.getResultList();

		return result.stream().map(this::mapObjectArrToEntity).toList();
	}

	@Override
	public SupplierDetail findBySupplierIdAndCommodityId(String supplierId, String commodityId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM supplier_details sd WHERE sd.commodity_id = :commodityId AND sd.supplier_id = :supplierId");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("supplierId", supplierId);
		query.setParameter("commodityId", commodityId);
		
		Object result = query.getSingleResult();
		
		Object[] resArr = (Object[]) result;

		return mapObjectArrToEntity(resArr);
	}

	@Override
	public Optional<SupplierDetail> findById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM supplier_details sd WHERE sd.id = :id");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		
		Object result = query.getSingleResult();

		Object[] resArr = (Object[]) result;
		
		SupplierDetail supplierDetail = mapObjectArrToEntity(resArr);
		
		return Optional.of(supplierDetail);
	}
	
	private SupplierDetail mapObjectArrToEntity(Object[] objArr) {
		SupplierDetail supplierDetail = new SupplierDetail();
		supplierDetail.setId(objArr[0].toString());
		
		Supplier supplier = new Supplier();
		supplier.setId(objArr[1].toString());
		supplierDetail.setSupplier(supplier);
		
		Commodity commodity = new Commodity();
		commodity.setId(objArr[2].toString());
		supplierDetail.setCommodity(commodity);
		
		supplierDetail.setAmount((BigDecimal)objArr[3]);			
		supplierDetail.setCreatedAt(ZonedDateTime.parse(objArr[4].toString()));
		
		if(objArr[5] != null) {
			supplierDetail.setUpdatedAt(ZonedDateTime.parse(objArr[5].toString()));			
		}else {
			supplierDetail.setUpdatedAt(null);
		}
		
		supplierDetail.setVersion(Long.valueOf(objArr[6].toString()));
		
		if(objArr[7] != null) {
			supplierDetail.setDeletedAt(ZonedDateTime.parse(objArr[7].toString()));			
		}else {
			supplierDetail.setDeletedAt(null);			
		}
		
		return supplierDetail;
	}

	@Override
	public SupplierDetail save(SupplierDetail supplierDetail) {
		if (supplierDetail.getId() != null) {
			supplierDetail = entityManager.merge(supplierDetail);
		} else {
			entityManager.persist(supplierDetail);
		}

		return supplierDetail;
	}

	@Override
	public SupplierDetail saveAndFlush(SupplierDetail supplierDetail) {
		SupplierDetail savedEntity = save(supplierDetail);

		if (entityManager.contains(savedEntity)) {
			entityManager.flush();
		}

		return supplierDetail;
	}

	@Override
	public boolean delete(SupplierDetail supplierDetail) {
		if (supplierDetail != null) {
			entityManager.remove(supplierDetail);
			return true;
		}
		return false;
	}
	
}
