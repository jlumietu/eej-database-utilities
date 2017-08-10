/**
 * 
 */
package com.eej.utilities.database;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author DOIBALMI
 *
 */
public interface SimpleHibernateDaoSupportEngine extends SimpleHibernateDaoSupportSpec {
	
	public abstract int getCoreHibernateVersion();

	public abstract SessionFactory getSessionFactory();
	
	public abstract void setSessionFactory(SessionFactory sessionFactory);
	
	public abstract HibernateTemplate getHibernate3Template();
	
	public abstract org.springframework.orm.hibernate4.HibernateTemplate getHibernate4Template();

}
