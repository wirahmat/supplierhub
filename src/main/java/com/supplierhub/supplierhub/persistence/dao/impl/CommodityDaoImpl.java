package com.supplierhub.supplierhub.persistence.dao.impl;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.dao.CommodityDao;
import com.supplierhub.supplierhub.persistence.entity.Category;
import com.supplierhub.supplierhub.persistence.entity.Commodity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class CommodityDaoImpl implements CommodityDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public boolean existsById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM commodities c WHERE c.id = :id)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public boolean existsByCode(String code) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM commodities c WHERE c.code = :code)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("code", code);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public List<Commodity> getAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM commodities");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		List<Object[]> result = query.getResultList();

		return result.stream().map(this::mapObjectArrToEntity).toList();
	}

	@Override
	public Optional<Commodity> findById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM commodities c WHERE c.id = :id");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		
		Object result = query.getSingleResult();

		Object[] resArr = (Object[]) result;
		
		Commodity commodity = mapObjectArrToEntity(resArr);
		
		return Optional.of(commodity);
	}

	private Commodity mapObjectArrToEntity(Object[] objArr) {
		Commodity commodity = new Commodity();
		commodity.setId(objArr[0].toString());
		commodity.setCode(objArr[1].toString());
		commodity.setName(objArr[2].toString());
		
		if(objArr[3] != null) {
			commodity.setDescription(objArr[3].toString());			
		}else {
			commodity.setDescription(null);
		}
		
		if(objArr[4] != null) {
			commodity.setRestockWhen(Integer.valueOf(objArr[4].toString()));
		}else {
			commodity.setRestockWhen(null);
		}
		
		commodity.setQuantity(Integer.valueOf(objArr[5].toString()));
		commodity.setRegisteredDate(LocalDate.parse(objArr[6].toString()));
		
		Category category = new Category();
		category.setId(objArr[7].toString());
		commodity.setCategory(category);
		
		commodity.setCreatedAt(ZonedDateTime.parse(objArr[8].toString()));
		
		if(objArr[9] != null) {
			commodity.setUpdatedAt(ZonedDateTime.parse(objArr[9].toString()));			
		}else {
			commodity.setUpdatedAt(null);
		}
		
		commodity.setVersion(Long.valueOf(objArr[10].toString()));
		commodity.setIsActive(Boolean.valueOf(objArr[11].toString()));
		
		if(objArr[12] != null) {
			commodity.setDeletedAt(ZonedDateTime.parse(objArr[12].toString()));			
		}else {
			commodity.setDeletedAt(null);			
		}
		
		return commodity;
	}

	@Override
	public Commodity save(Commodity commodity) {
		if (commodity.getId() != null) {
			commodity = entityManager.merge(commodity);
		} else {
			entityManager.persist(commodity);
		}

		return commodity;
	}

	@Override
	public Commodity saveAndFlush(Commodity commodity) {
		Commodity savedEntity = save(commodity);

		if (entityManager.contains(savedEntity)) {
			entityManager.flush();
		}

		return commodity;
	}

	@Override
	public boolean delete(Commodity commodity) {
		if (commodity != null) {
			commodity.setDeletedAt(ZonedDateTime.now());
		}
		return false;
	}
}
