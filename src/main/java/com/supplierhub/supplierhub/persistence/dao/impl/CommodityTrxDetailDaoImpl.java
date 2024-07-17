package com.supplierhub.supplierhub.persistence.dao.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.dao.CommodityTrxDetailDao;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrx;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrxDetail;
import com.supplierhub.supplierhub.persistence.entity.Supplier;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class CommodityTrxDetailDaoImpl implements CommodityTrxDetailDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean existsById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM commodity_trx_details ctd WHERE ctd.id = :id)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public boolean existsByCommodityTrxIdAndSupplierId(String commodityTrxId, String supplierId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM commodity_trx_details ctd WHERE ctd.commodity_trx_id = :commodityTrxId AND ctd.supplier_id = :supplierId)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("commodityTrxId", commodityTrxId);
		query.setParameter("supplierId", supplierId);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public List<CommodityTrxDetail> getAllByHeaderId(String headerId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM commodity_trx_details ctd WHERE ctd.commodity_trx_id = :commodityTrxId");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("commodityTrxId", headerId);
		List<Object[]> result = query.getResultList();

		return result.stream().map(this::mapObjectArrToEntity).toList();
	}

	@Override
	public Optional<CommodityTrxDetail> findById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM commodity_trx_details ctd WHERE ctd.id = :id");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		
		Object result = query.getSingleResult();

		Object[] resArr = (Object[]) result;
		
		CommodityTrxDetail commodityTrxDetail = mapObjectArrToEntity(resArr);
		
		return Optional.of(commodityTrxDetail);
	}

	@Override
	public Long getCount() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(ctd.id) FROM commodity_trx_details ctd ");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		Object result = query.getSingleResult();

		return Long.valueOf(result.toString());
	}
	
	private CommodityTrxDetail mapObjectArrToEntity(Object[] objArr) {
		CommodityTrxDetail commodityTrxDetail = new CommodityTrxDetail();
		commodityTrxDetail.setId(objArr[0].toString());
		
		CommodityTrx commodityTrx = new CommodityTrx();
		commodityTrx.setId(objArr[1].toString());
		commodityTrxDetail.setCommodityTrx(commodityTrx);
		
		Supplier supplier = new Supplier();
		supplier.setId(objArr[2].toString());
		commodityTrxDetail.setSupplier(supplier);
		
		commodityTrxDetail.setCreatedAt(ZonedDateTime.parse(objArr[3].toString()));
		
		if(objArr[4] != null) {
			commodityTrxDetail.setUpdatedAt(ZonedDateTime.parse(objArr[4].toString()));			
		}else {
			commodityTrxDetail.setUpdatedAt(null);
		}
		
		commodityTrxDetail.setVersion(Long.valueOf(objArr[5].toString()));
		
		if(objArr[6] != null) {
			commodityTrxDetail.setDeletedAt(ZonedDateTime.parse(objArr[6].toString()));			
		}else {
			commodityTrxDetail.setDeletedAt(null);			
		}
		
		return commodityTrxDetail;
	}
}
