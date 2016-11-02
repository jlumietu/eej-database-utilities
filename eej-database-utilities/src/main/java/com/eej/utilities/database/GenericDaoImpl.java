/**
 * 
 */
package com.eej.utilities.database;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eej.utilities.model.DataTablePaginationRequest;

/**
 * @author jlumietu - Mikel Ibiricu Alfaro
 *
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
public class GenericDaoImpl extends DataTableHibernateDaoSupport implements
		GenericDao {

	/* (non-Javadoc)
	 * @see com.ib.utilities.database.DataTableHibernateDaoSupport#getListViewCount(com.ib.utilities.model.DataTablePaginationRequest, java.lang.Class)
	 */
	@Override
	public int getListViewCount(DataTablePaginationRequest request,
			Class<? extends Serializable> clazz) {
		return super.getListViewCount(request, clazz);
	}

	/* (non-Javadoc)
	 * @see com.ib.utilities.database.DataTableHibernateDaoSupport#getListViewCount(com.ib.utilities.model.DataTablePaginationRequest, java.lang.Class, java.lang.String)
	 */
	@Override
	public int getListViewCount(DataTablePaginationRequest request,
			Class<? extends Serializable> clazz, String countDistinctParam) {
		return super.getListViewCount(request, clazz, countDistinctParam);
	}

	/* (non-Javadoc)
	 * @see com.ib.utilities.database.DataTableHibernateDaoSupport#getListView(com.ib.utilities.model.DataTablePaginationRequest, java.lang.Class)
	 */
	@Override
	public List<? extends Serializable> getListView(
			DataTablePaginationRequest request,
			Class<? extends Serializable> clazz) {
		return super.getListView(request, clazz);
	}
	
	

}
