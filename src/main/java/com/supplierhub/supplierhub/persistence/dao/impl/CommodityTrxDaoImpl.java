package com.supplierhub.supplierhub.persistence.dao.impl;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.dao.CommodityTrxDao;
import com.supplierhub.supplierhub.persistence.entity.CommodityTrx;
import com.supplierhub.supplierhub.persistence.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class CommodityTrxDaoImpl implements CommodityTrxDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean existsById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM commodity_trx ct WHERE ct.id = :id)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public boolean existsByTrxNumberAndTrxDate(String trxNumber, LocalDate trxDate) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM commodity_trx ct WHERE ct.trx_number = :trxNumber AND ct.trx_date = :trx_date)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("trxNumber", trxNumber);
		query.setParameter("trxDate", trxDate);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public List<CommodityTrx> getAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM commodity_trx");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		List<Object[]> result = query.getResultList();

		return result.stream().map(this::mapObjectArrToEntity).toList();
	}

	@Override
	public Optional<CommodityTrx> findById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM commodity_trx ct WHERE ct.id = :id");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		
		Object result = query.getSingleResult();

		Object[] resArr = (Object[]) result;
		
		CommodityTrx commodityTrx = mapObjectArrToEntity(resArr);
		
		return Optional.of(commodityTrx);
	}

	@Override
	public Long getCount() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(ct.id) FROM commodity_trx ct ");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		Object result = query.getSingleResult();

		return Long.valueOf(result.toString());
	}

	private CommodityTrx mapObjectArrToEntity(Object[] objArr) {
		CommodityTrx commodityTrx = new CommodityTrx();
		commodityTrx.setId(objArr[0].toString());
		commodityTrx.setTrxNumber(objArr[1].toString());
		commodityTrx.setTrxDate(LocalDate.parse(objArr[2].toString()));
		
		User user = new User();
		user.setId(objArr[3].toString());
		commodityTrx.setUser(user);			
		
		commodityTrx.setCreatedAt(ZonedDateTime.parse(objArr[4].toString()));
		
		if(objArr[5] != null) {
			commodityTrx.setUpdatedAt(ZonedDateTime.parse(objArr[5].toString()));			
		}else {
			commodityTrx.setUpdatedAt(null);
		}
		
		commodityTrx.setVersion(Long.valueOf(objArr[6].toString()));
		
		if(objArr[7] != null) {
			commodityTrx.setDeletedAt(ZonedDateTime.parse(objArr[7].toString()));			
		}else {
			commodityTrx.setDeletedAt(null);			
		}
		
		return commodityTrx;
	}

	@Override
	public CommodityTrx save(CommodityTrx commodityTrx) {
		if (commodityTrx.getId() != null) {
			commodityTrx = entityManager.merge(commodityTrx);
		} else {
			entityManager.persist(commodityTrx);
		}

		return commodityTrx;
	}

	@Override
	public CommodityTrx saveAndFlush(CommodityTrx commodityTrx) {
		CommodityTrx savedEntity = save(commodityTrx);

		if (entityManager.contains(savedEntity)) {
			entityManager.flush();
		}

		return commodityTrx;
	}

	@Override
	public boolean delete(CommodityTrx commodityTrx) {
		if (commodityTrx != null) {
			entityManager.remove(commodityTrx);
			return true;
		}
		return false;
	}
}
