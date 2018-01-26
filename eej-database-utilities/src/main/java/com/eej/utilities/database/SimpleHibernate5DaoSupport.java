/**
 * 
 */
package com.eej.utilities.database;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.eej.utilities.model.DataTablePaginationRequest;

/**
 * @author jlumietu - Mikel Ibiricu Alfaro
 *
 */
public class SimpleHibernate5DaoSupport extends HibernateDaoSupport implements SimpleHibernateDaoSupportEngine {

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
		throw new IllegalStateException("Using Hibernate 5 so no Hibernate3Template can be retrieved");
	}

	/* (non-Javadoc)
	 * @see com.eej.utilities.database.SimpleHibernateDaoSupportEngine#getHibernate4Template()
	 */
	@Override
	public org.springframework.orm.hibernate4.HibernateTemplate getHibernate4Template() {
		throw new IllegalStateException("Using Hibernate 5 so no Hibernate3Template can be retrieved");
	}

	@Override
	public org.springframework.orm.hibernate5.HibernateTemplate getHibernate5Template() {
		return this.getHibernate5Template();
	}

	@Override
	public <T> T get(Class<T> entityClass, Serializable id) throws DataAccessException {
		return this.getHibernate5Template().get(entityClass, id);
	}	
	
	@Override
	public <T> T get(Class<T> entityClass, Serializable id, LockMode lockMode) {
		return this.getHibernate5Template().get(entityClass, id, lockMode);
	}

	@Override
	public void saveOrUpdate(Object entity) throws DataAccessException {
		this.getHibernate5Template().saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdate(String entityName, Object entity) throws DataAccessException {
		this.getHibernate5Template().saveOrUpdate(entityName, entity);
	}

	@Override
	public void update(Object entity) throws DataAccessException {
		this.getHibernate5Template().update(entity);
	}

}
