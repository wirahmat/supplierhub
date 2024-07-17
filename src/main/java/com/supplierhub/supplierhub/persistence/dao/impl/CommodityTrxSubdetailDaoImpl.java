package com.supplierhub.supplierhub.persistence.dao.impl;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.dao.CommodityTrxSubdetailDao;
import com.supplierhub.supplierhub.persistence.entity.Commodity;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrxDetail;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrxSubdetail;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class CommodityTrxSubdetailDaoImpl implements CommodityTrxSubdetailDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean existsById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM commodity_trx_subdetails cts WHERE cts.id = :id)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public boolean existsByCommodityTrxDetailIdAndCommodityId(String commodityTrxDetailId, String commodityId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM commodity_trx_subdetails cts WHERE cts.commodity_trx_detail_id = :commodityTrxDetailId AND cts.commodity_id = :commodityId)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("commodityTrxDetailId", commodityTrxDetailId);
		query.setParameter("commodityId", commodityId);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public List<CommodityTrxSubdetail> getAllByDetailId(String detailId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM commodity_trx_subdetails cts WHERE cts.commodity_trx_detail_id = :commodityTrxDetailId");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("commodityTrxDetailId", detailId);
		List<Object[]> result = query.getResultList();

		return result.stream().map(this::mapObjectArrToEntity).toList();
	}

	@Override
	public Optional<CommodityTrxSubdetail> findById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM commodity_trx_subdetails cts WHERE cts.id = :id");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		
		Object result = query.getSingleResult();

		Object[] resArr = (Object[]) result;
		
		CommodityTrxSubdetail commodityTrxSubdetail = mapObjectArrToEntity(resArr);
		
		return Optional.of(commodityTrxSubdetail);
	}

	@Override
	public Long getCount() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(cts.id) FROM commodity_trx_subdetails cts ");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		Object result = query.getSingleResult();

		return Long.valueOf(result.toString());
	}
	
	private CommodityTrxSubdetail mapObjectArrToEntity(Object[] objArr) {
		CommodityTrxSubdetail commodityTrxSubdetail = new CommodityTrxSubdetail();
		commodityTrxSubdetail.setId(objArr[0].toString());
		
		CommodityTrxDetail commodityTrxDetail = new CommodityTrxDetail();
		commodityTrxDetail.setId(objArr[1].toString());
		commodityTrxSubdetail.setCommodityTrxDetail(commodityTrxDetail);
		
		Commodity commodity = new Commodity();
		commodity.setId(objArr[2].toString());
		commodityTrxSubdetail.setCommodity(commodity);
		
		commodityTrxSubdetail.setQuantity(Integer.valueOf(objArr[3].toString()));
		commodityTrxSubdetail.setTotalAmount((BigDecimal)objArr[4]);
		commodityTrxSubdetail.setCreatedAt(ZonedDateTime.parse(objArr[5].toString()));
		
		if(objArr[6] != null) {
			commodityTrxSubdetail.setUpdatedAt(ZonedDateTime.parse(objArr[6].toString()));			
		}else {
			commodityTrxSubdetail.setUpdatedAt(null);
		}
		
		commodityTrxSubdetail.setVersion(Long.valueOf(objArr[7].toString()));
		
		if(objArr[8] != null) {
			commodityTrxSubdetail.setDeletedAt(ZonedDateTime.parse(objArr[8].toString()));			
		}else {
			commodityTrxSubdetail.setDeletedAt(null);			
		}
		
		return commodityTrxSubdetail;
	}
	
	
}
