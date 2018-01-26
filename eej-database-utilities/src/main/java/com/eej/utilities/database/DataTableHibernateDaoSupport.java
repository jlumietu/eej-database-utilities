/**
 * 
 */
package com.eej.utilities.database;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author jlumietu - Mikel Ibiricu Alfaro
 *
 */
public class DataTableHibernateDaoSupport extends DataTableBaseHibernateDaoSupport {

	@Autowired
	public void anyMethodName(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}	
	
}
