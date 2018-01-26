/**
 * 
 */
package com.eej.utilities.database;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author jlumietu - Mikel Ibiricu Alfaro
 *
 */
@SuppressWarnings("deprecation")
public interface SimpleHibernateDaoSupportEngine extends SimpleHibernateDaoSupportSpec {
	
	public abstract int getCoreHibernateVersion();

	public abstract SessionFactory getSessionFactory();
	
	public abstract void setSessionFactory(SessionFactory sessionFactory);
	
	public abstract HibernateTemplate getHibernate3Template();
	
	public abstract org.springframework.orm.hibernate4.HibernateTemplate getHibernate4Template();
	
	public abstract org.springframework.orm.hibernate5.HibernateTemplate getHibernate5Template();

	public abstract <T> T get(Class<T> entityClass, Serializable id) throws DataAccessException;

}
