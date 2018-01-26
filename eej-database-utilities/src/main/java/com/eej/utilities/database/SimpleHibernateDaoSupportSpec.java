package com.eej.utilities.database;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;

import com.eej.utilities.model.DataTablePaginationRequest;

public interface SimpleHibernateDaoSupportSpec {

	public abstract void anyMethodName(SessionFactory sessionFactory);

	/**
	 * 
	 * @param criteria
	 * @param request
	 * @param clazz
	 * @param applyOrder 
	 * @return
	 */
	public abstract Criteria applyPaginationRequestFilters(Criteria criteria, DataTablePaginationRequest request, Class<?> clazz,
			boolean applyOrder, Class<? extends Serializable> criteriaRootClass);
	

}