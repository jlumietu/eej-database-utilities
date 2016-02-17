/**
 * 
 */
package com.eej.utilities.database;

import org.hibernate.SessionFactory;

/**
 * @author DOIBALMI
 *
 */
public abstract class CustomHibernateDaoSupport extends DataTableBaseHibernateDaoSupport {

	private SessionFactory sessionFactory;

	/**
	 * @param sessionFactory
	 */
	public CustomHibernateDaoSupport(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
		this.setSessionFactory(sessionFactory);
	}

}
