/**
 * 
 */
package com.eej.utilities.database;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.eej.utilities.model.DataTablePaginationRequest;

/**
 * @author DOIBALMI
 *
 */
public abstract class SimpleHibernate4DaoSupport extends HibernateDaoSupport implements SimpleHibernateDaoSupportEngine {

	private PaginationRequestFilterCriteriaHelper paginationRequestFilterCriteriaHelper;

	/* (non-Javadoc)
	 * @see com.eej.ssba2.util.database.SimpleHibernateDaoSupportSpec#anyMethodName(org.hibernate.SessionFactory)
	 */
	@Override
	public void anyMethodName(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	/* (non-Javadoc)
	 * @see com.eej.ssba2.util.database.SimpleHibernateDaoSupportSpec#applyPaginationRequestFilters(org.hibernate.Criteria, com.eej.utilities.model.DataTablePaginationRequest, java.lang.Class, boolean, java.lang.Class)
	 */
	@Override
	public Criteria applyPaginationRequestFilters(Criteria criteria, DataTablePaginationRequest request, Class<?> clazz,
			boolean applyOrder, Class<? extends Serializable> criteriaRootClass) {
		return paginationRequestFilterCriteriaHelper.applyPaginationRequestFilters(criteria, request, clazz, applyOrder, criteriaRootClass);
	}

	@Override
	public final int getCoreHibernateVersion() {
		return 4;
	}

	/* (non-Javadoc)
	 * @see com.eej.utilities.database.SimpleHibernateDaoSupportEngine#getHibernate3Template()
	 */
	@Override
	public HibernateTemplate getHibernate3Template() {
		throw new IllegalStateException("Using Hibernate 4 so no Hibernate3Template can be retrieved");
	}

	/* (non-Javadoc)
	 * @see com.eej.utilities.database.SimpleHibernateDaoSupportEngine#getHibernate4Template()
	 */
	@Override
	public org.springframework.orm.hibernate4.HibernateTemplate getHibernate4Template() {
		return this.getHibernateTemplate();
	}
	
	

}
