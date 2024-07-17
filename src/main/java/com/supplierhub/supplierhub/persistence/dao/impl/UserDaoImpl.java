package com.supplierhub.supplierhub.persistence.dao.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.supplierhub.supplierhub.persistence.dao.UserDao;
import com.supplierhub.supplierhub.persistence.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class UserDaoImpl implements UserDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean existsById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM users u WHERE u.id = :id)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public boolean existsByEmail(String email) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EXISTS (SELECT 1 FROM users u WHERE u.email = :email)");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("email", email);
		Object result = query.getSingleResult();

		return Boolean.valueOf(result.toString());
	}

	@Override
	public List<User> getAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM users");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		List<Object[]> result = query.getResultList();

		return result.stream().map(this::mapObjectArrToEntity).toList();
	}

	@Override
	public Optional<User> findById(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM users u WHERE u.id = :id");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		
		Object result = query.getSingleResult();

		Object[] resArr = (Object[]) result;
		
		User user = mapObjectArrToEntity(resArr);
		
		return Optional.of(user);
	}
	
	@Override
	public Optional<User> findByEmail(String email) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM users u WHERE u.email = :email");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("email", email);
		
		Object result = query.getSingleResult();

		Object[] resArr = (Object[]) result;
		
		User user = mapObjectArrToEntity(resArr);
		
		return Optional.of(user);
	}
	
	private User mapObjectArrToEntity(Object[] objArr) {
		User user = new User();
		user.setId(objArr[0].toString());
		user.setFullName(objArr[1].toString());
		user.setEmail(objArr[2].toString());
		user.setPassword(objArr[3].toString());			
		
		user.setCreatedAt(ZonedDateTime.parse(objArr[4].toString()));
		
		if(objArr[5] != null) {
			user.setUpdatedAt(ZonedDateTime.parse(objArr[5].toString()));			
		}else {
			user.setUpdatedAt(null);
		}
		
		user.setVersion(Long.valueOf(objArr[6].toString()));
		user.setIsActive(Boolean.valueOf(objArr[7].toString()));
		
		if(objArr[8] != null) {
			user.setDeletedAt(ZonedDateTime.parse(objArr[8].toString()));			
		}else {
			user.setDeletedAt(null);			
		}
		
		return user;
	}

}
