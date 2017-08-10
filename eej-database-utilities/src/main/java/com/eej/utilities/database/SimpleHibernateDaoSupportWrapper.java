/**
 * 
 */
package com.eej.utilities.database;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import com.eej.utilities.model.DataTablePaginationRequest;

/**
 * @author DOIBALMI
 *
 */
public abstract class SimpleHibernateDaoSupportWrapper implements SimpleHibernateDaoSupportSpec {
	
	private SimpleHibernateDaoSupportEngine simpleHibernateDaoSupport;
	
	/**
	 * @return the simpleHibernateDaoSupport
	 */
	public SimpleHibernateDaoSupportEngine getSimpleHibernateDaoSupport() {
		return simpleHibernateDaoSupport;
	}

	/**
	 * @param simpleHibernateDaoSupport the simpleHibernateDaoSupport to set
	 */
	public void setSimpleHibernateDaoSupport(SimpleHibernateDaoSupportEngine simpleHibernateDaoSupport) {
		this.simpleHibernateDaoSupport = simpleHibernateDaoSupport;
	}

	/* (non-Javadoc)
	 * @see com.eej.ssba2.util.database.SimpleHibernateDaoSupportSpec#anyMethodName(org.hibernate.SessionFactory)
	 */
	@Override
	public void anyMethodName(SessionFactory sessionFactory) {
		this.simpleHibernateDaoSupport.anyMethodName(sessionFactory);

	}
	
	public SessionFactory getSessionFactory(){
		return this.simpleHibernateDaoSupport.getSessionFactory();
	}
	
	/**
	 * @return
	 * @see com.eej.utilities.database.SimpleHibernateDaoSupportEngine#getCoreHibernateVersion()
	 */
	public int getCoreHibernateVersion() {
		return simpleHibernateDaoSupport.getCoreHibernateVersion();
	}

	/**
	 * @param sessionFactory
	 * @see com.eej.utilities.database.SimpleHibernateDaoSupportEngine#setSessionFactory(org.hibernate.SessionFactory)
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		if(this.simpleHibernateDaoSupport != null){
			simpleHibernateDaoSupport.setSessionFactory(sessionFactory);
		}
	}

	/* (non-Javadoc)
	 * @see com.eej.ssba2.util.database.SimpleHibernateDaoSupportSpec#applyPaginationRequestFilters(org.hibernate.Criteria, com.eej.utilities.model.DataTablePaginationRequest, java.lang.Class, boolean, java.lang.Class)
	 */
	@Override
	public Criteria applyPaginationRequestFilters(Criteria criteria, DataTablePaginationRequest request, Class<?> clazz,
			boolean applyOrder, Class<? extends Serializable> criteriaRootClass) {
		return this.simpleHibernateDaoSupport.applyPaginationRequestFilters(criteria, request, clazz, applyOrder, criteriaRootClass);
	}

}
