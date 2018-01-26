/**
 * 
 */
package com.eej.utilities.database;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.eej.utilities.model.DataTablePaginationRequest;

/**
 * @author jlumietu - Mikel Ibiricu Alfaro
 *
 */
public class SimpleHibernate4DaoSupport extends HibernateDaoSupport implements SimpleHibernateDaoSupportEngine {

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
	@SuppressWarnings("deprecation")
	@Override
	public org.springframework.orm.hibernate3.HibernateTemplate getHibernate3Template() {
		throw new IllegalStateException("Using Hibernate 4 so no Hibernate3Template can be retrieved");
	}

	/* (non-Javadoc)
	 * @see com.eej.utilities.database.SimpleHibernateDaoSupportEngine#getHibernate4Template()
	 */
	@Override
	public org.springframework.orm.hibernate4.HibernateTemplate getHibernate4Template() {
		return this.getHibernateTemplate();
	}

	@Override
	public org.springframework.orm.hibernate5.HibernateTemplate getHibernate5Template() {
		throw new IllegalStateException("Using Hibernate 4 so no Hibernate5Template can be retrieved");
	}

	/* (non-Javadoc)
	 * @see com.eej.utilities.database.SimpleHibernateDaoSupportEngine#get(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <T> T get(Class<T> entityClass, Serializable id) throws DataAccessException {
		return this.getHibernate4Template().get(entityClass, id);
	}
	
	

}
