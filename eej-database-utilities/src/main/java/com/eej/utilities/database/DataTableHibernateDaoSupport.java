/**
 * 
 */
package com.eej.utilities.database;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;




/**
 * @author jlumietu - Mikel Ibiricu Alfaro
 *
 */
public class DataTableHibernateDaoSupport extends DataTableBaseHibernateDaoSupport {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public void anyMethodName(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}	
	
}
