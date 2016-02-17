/**
 * 
 */
package com.eej.utilities.database;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eej.utilities.model.DataTablePaginationRequest;

/**
 * @author jlumietu
 *
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
public class GenericDaoPluggableSessionFactoryImpl extends
		CustomHibernateDaoSupport implements GenericDao {

	public GenericDaoPluggableSessionFactoryImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/* (non-Javadoc)
	 * @see com.ib.utilities.database.GenericDao#getListView(com.ib.utilities.model.DataTablePaginationRequest, java.lang.Class)
	 */
	@Override
	public List<? extends Serializable> getListView(
			DataTablePaginationRequest request,
			Class<? extends Serializable> clazz) {
		return super.getListView(request, clazz);
	}

	/* (non-Javadoc)
	 * @see com.ib.utilities.database.GenericDao#getListViewCount(com.ib.utilities.model.DataTablePaginationRequest, java.lang.Class)
	 */
	@Override
	public int getListViewCount(DataTablePaginationRequest request,
			Class<? extends Serializable> clazz) {
		return super.getListViewCount(request, clazz);
	}

	/* (non-Javadoc)
	 * @see com.ib.utilities.database.GenericDao#getListViewCount(com.ib.utilities.model.DataTablePaginationRequest, java.lang.Class, java.lang.String)
	 */
	@Override
	public int getListViewCount(DataTablePaginationRequest request,
			Class<? extends Serializable> clazz, String countDistinctParam) {
		return super.getListViewCount(request, clazz, countDistinctParam);
	}

}
