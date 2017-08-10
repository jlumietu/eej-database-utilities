package com.eej.utilities.database;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.eej.utilities.database.PaginationRequestFilterCriteriaHelper;
import com.eej.utilities.database.SimpleHibernateDaoSupportEngine;
import com.eej.utilities.model.DataTablePaginationRequest;

public class SimpleHibernateDaoSupport extends HibernateDaoSupport implements SimpleHibernateDaoSupportEngine{
	
	private PaginationRequestFilterCriteriaHelper paginationRequestFilterCriteriaHelper;
	
	/**
	 * 
	 */
	public SimpleHibernateDaoSupport() {
		super();
		this.paginationRequestFilterCriteriaHelper = new PaginationRequestFilterCriteriaHelper();
	}

	/* (non-Javadoc)
	 * @see com.eej.ssba2.util.database.SimpleHibernateDaoSupportSpec#anyMethodName(org.hibernate.SessionFactory)
	 */
	@Override
	@Autowired
	public void anyMethodName(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	/* (non-Javadoc)
	 * @see com.eej.ssba2.util.database.SimpleHibernateDaoSupportSpec#applyPaginationRequestFilters(org.hibernate.Criteria, com.eej.utilities.model.DataTablePaginationRequest, java.lang.Class, boolean, java.lang.Class)
	 */
	@Override
	public final Criteria applyPaginationRequestFilters(Criteria criteria,
			DataTablePaginationRequest request, Class<?> clazz, boolean applyOrder, Class<? extends Serializable> criteriaRootClass) {
		this.getHibernateTemplate();
		return paginationRequestFilterCriteriaHelper.applyPaginationRequestFilters(criteria, request, clazz, applyOrder, criteriaRootClass);
	}

	/* (non-Javadoc)
	 * @see com.eej.ssba2.util.database.SimpleHibernateDaoSupportEngine#getCoreHibernateVersion()
	 */
	@Override
	public final int getCoreHibernateVersion() {
		return 3;
	}

	public static void main(String args[]){
		try{
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			System.out.println(dateFormat.parse("10/11/2014"));
		}catch(Throwable t){
			System.out.println(t.getMessage());
			t.printStackTrace(System.out);
		}
	}

	/* (non-Javadoc)
	 * @see com.eej.utilities.database.SimpleHibernateDaoSupportEngine#getHibernate3Template()
	 */
	@Override
	public HibernateTemplate getHibernate3Template() {
		return this.getHibernateTemplate();
	}

	/* (non-Javadoc)
	 * @see com.eej.utilities.database.SimpleHibernateDaoSupportEngine#getHibernate4Template()
	 */
	@Override
	public org.springframework.orm.hibernate4.HibernateTemplate getHibernate4Template() {
		throw new IllegalStateException("Using Hibernate 3 so no Hibernate4Template can be retrieved");
	}
	
	
}