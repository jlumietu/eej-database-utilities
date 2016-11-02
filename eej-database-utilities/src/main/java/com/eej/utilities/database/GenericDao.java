/**
 * 
 */
package com.eej.utilities.database;

import java.io.Serializable;
import java.util.List;

import com.eej.utilities.datatables.SourceData;
import com.eej.utilities.model.DataTablePaginationRequest;

/**
 * @author jlumietu - Mikel Ibiricu Alfaro
 *
 */
public interface GenericDao extends SourceData{

	List<? extends Serializable> getListView(
			DataTablePaginationRequest request, 
			Class<? extends Serializable> clazz);
	
	int getListViewCount(DataTablePaginationRequest request, 
			Class<? extends Serializable> clazz);
	
	int getListViewCount(DataTablePaginationRequest request, 
			Class<? extends Serializable> clazz, String countDistinctParam);
}
