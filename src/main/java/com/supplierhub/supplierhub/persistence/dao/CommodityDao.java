package com.supplierhub.supplierhub.persistence.dao;

import java.util.List;
import java.util.Optional;

import com.supplierhub.supplierhub.persistence.entity.Commodity;

public interface CommodityDao {

	boolean existsById(String id);

	boolean existsByCode(String code);

	List<Commodity> getAll();

	Optional<Commodity> findById(String id);

}
