package com.eej.utilities.database;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import com.eej.utilities.model.DataTablePaginationRequest;

public interface SimpleHibernateDaoSupportSpec {

	void anyMethodName(SessionFactory sessionFactory);

	/**
	 * 
	 * @param criteria
	 * @param request
	 * @param clazz
	 * @param applyOrder 
	 * @return
	 */
	Criteria applyPaginationRequestFilters(Criteria criteria, DataTablePaginationRequest request, Class<?> clazz,
			boolean applyOrder, Class<? extends Serializable> criteriaRootClass);

}