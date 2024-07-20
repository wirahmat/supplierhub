package com.supplierhub.supplierhub.persistence.dao.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.dao.CategoryDao;
import com.supplierhub.supplierhub.persistence.entity.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class CategoryDaoImpl implements CategoryDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean existsById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM categories c WHERE c.id = :id)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public boolean existsByCode(String code) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM categories c WHERE c.code = :code)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("code", code);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public List<Category> getAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM categories");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		List<Object[]> result = query.getResultList();

		return result.stream().map(this::mapObjectArrToEntity).toList();
	}

	@Override
	public Optional<Category> findById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM categories c WHERE c.id = :id");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		
		Object result = query.getSingleResult();

		Object[] resArr = (Object[]) result;
		
		Category category = mapObjectArrToEntity(resArr);
		
		return Optional.of(category);
	}

	private Category mapObjectArrToEntity(Object[] objArr) {
		Category category = new Category();
		category.setId(objArr[0].toString());
		category.setCode(objArr[1].toString());
		category.setName(objArr[2].toString());
		
		if(objArr[3] != null) {
			category.setDescription(objArr[3].toString());			
		}else {
			category.setDescription(null);
		}
		
		category.setCreatedAt(ZonedDateTime.parse(objArr[4].toString()));
		
		if(objArr[5] != null) {
			category.setUpdatedAt(ZonedDateTime.parse(objArr[5].toString()));			
		}else {
			category.setUpdatedAt(null);
		}
		
		category.setVersion(Long.valueOf(objArr[6].toString()));
		category.setIsActive(Boolean.valueOf(objArr[7].toString()));
		
		if(objArr[8] != null) {
			category.setDeletedAt(ZonedDateTime.parse(objArr[8].toString()));			
		}else {
			category.setDeletedAt(null);			
		}
		
		return category;
	}

	@Override
	public Category save(Category category) {
		if (category.getId() != null) {
			category = entityManager.merge(category);
		} else {
			entityManager.persist(category);
		}

		return category;
	}

	@Override
	public Category saveAndFlush(Category category) {
		Category savedEntity = save(category);

		if (entityManager.contains(savedEntity)) {
			entityManager.flush();
		}

		return category;
	}

	@Override
	public boolean delete(Category category) {
		if (category != null) {
			entityManager.remove(category);
			return true;
		}
		return false;
	}

}
