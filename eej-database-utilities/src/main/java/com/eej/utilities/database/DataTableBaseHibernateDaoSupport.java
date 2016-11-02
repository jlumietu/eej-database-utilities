/**
 * 
 */
package com.eej.utilities.database;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.eej.utilities.annotation.DataTable;
import com.eej.utilities.annotation.DataTableColumn;
import com.eej.utilities.annotation.DataTableRowOwnerId;
import com.eej.utilities.database.generators.TypeRestrictionGeneratorFactory;
import com.eej.utilities.model.DataTablePaginationRequest;
import com.erax.principal.PrincipalSerializableIdLocator;

/**
 * @author jlumietu
 *
 */
public abstract class DataTableBaseHibernateDaoSupport extends
		HibernateDaoSupport {
	
	private TypeRestrictionGeneratorFactory typeRestrictionGeneratorFactory;
	
	private PrincipalSerializableIdLocator principalSerializableIdLocator = null;
	
	/**
	 * @return the typeRestrictionGeneratorFactory
	 */
	public TypeRestrictionGeneratorFactory getTypeRestrictionGeneratorFactory() {
		return typeRestrictionGeneratorFactory;
	}

	/**
	 * @param typeRestrictionGeneratorFactory the typeRestrictionGeneratorFactory to set
	 */
	public void setTypeRestrictionGeneratorFactory(
			TypeRestrictionGeneratorFactory typeRestrictionGeneratorFactory) {
		this.typeRestrictionGeneratorFactory = typeRestrictionGeneratorFactory;
	}

	/**
	 * 
	 * @param criteria
	 * @param request
	 * @param clazz
	 * @param applyOrder 
	 * @return
	 */
	public final Criteria applyPaginationRequestFilters(Criteria criteria,
			DataTablePaginationRequest request, Class<?> clazz, boolean applyOrder, Class<? extends Serializable> criteriaRootClass) {
		
		Map<String, String> columnFilters = request.getColsFilter();
		boolean applyOrdenation = false;
		String colOrderBy = null;
		String colOrderDirection = "asc";
		if(request.getDirOrd()!=null){
			colOrderDirection = request.getDirOrd(); 
		}
		String searchGeneral = request.getSearchGeneral();
		
		Map<String, String> columnSearchableAnnotatedColumn = new TreeMap<String, String>();
		List<String> generalSearchableColumn = new ArrayList<String>();
		if(clazz.isAnnotationPresent(DataTable.class)){
			for(Field f : clazz.getDeclaredFields()){
				if(f.isAnnotationPresent(DataTableColumn.class)){
					DataTableColumn column = f.getAnnotation(DataTableColumn.class);
					if(column.searchable()){
						generalSearchableColumn.add(f.getName());
					}
					if(column.columnSearch()){
						columnSearchableAnnotatedColumn.put(String.valueOf(column.colIndex()),f.getName());
					}
					if(String.valueOf(column.colIndex()).equals(request.getColOrd())){
						if(column.sortable()){
							applyOrdenation = true;
							colOrderBy = f.getName();
						}
					}
				}
				if(f.isAnnotationPresent(DataTableRowOwnerId.class)){
					if(this.principalSerializableIdLocator == null){
						logger.warn("DataTableRowOwnerId annotation found but no " + PrincipalSerializableIdLocator.class + " found for dependency");
					}else{
						criteria.add(
								Restrictions.eq(
										f.getName(), 
										this.principalSerializableIdLocator.findPrincipalSerializableId().getId()
									)
								);
					}
				}
			}
		}
		
		// Filtros de clumnas, aplicar a Criteria
		if(columnFilters != null && columnFilters.size() > 0){
			for(String s : columnSearchableAnnotatedColumn.keySet()){
				if(columnFilters.containsKey(s)){
					String filter = columnFilters.get(s);  
					if(filter != null && !filter.equals("")){
						try{
							logger.debug("clazz.getclass " + clazz.getName());
							/*Field theField = null;
							for(Field f : clazz.getDeclaredFields()){
								logger.debug("f.getName=" + f.getName());
								if(f.getName().equals(columnSearchableAnnotatedColumn.get(s))){
									theField = f;
								}
								if(theField != null){
									logger.debug("theFieldFound!=" + f.getName());
									break;
								}
							}*/
							Field theField = clazz.getDeclaredField(columnSearchableAnnotatedColumn.get(s));
							logger.debug("columnSearchableAnnotatedColumn.get(s) = " + columnSearchableAnnotatedColumn.get(s));
							Criterion c = this.generateCriterion(theField, columnSearchableAnnotatedColumn.get(s), criteriaRootClass, filter);
							if(c != null){
								criteria.add(c);
							}
						}catch(Throwable t){
							logger.error("Error: " + t.getMessage(), t);
						}
					}
				}
			}
		}
		
		// Busqueda general, aplicar a criteria
		if(searchGeneral != null && !searchGeneral.equals("")){
			Disjunction d = Restrictions.disjunction();
			boolean applyDisjunction = false; 
			for(String s : generalSearchableColumn){
				try{
					Field theField = clazz.getDeclaredField(s);
					Criterion c = this.generateCriterion(theField, s, criteriaRootClass, searchGeneral);
					logger.debug("GeneralSearchableColumn " + s + " -> generatedCriteria = " + c);
					if(c != null){
						d.add(c);
						applyDisjunction = true;
					}
				}catch(Exception e){
					logger.warn("Error : " + e.getMessage(), e);
				}
				//d.add(Restrictions.ilike(s, searchGeneral.concat("%")));
			}
			if(applyDisjunction){
				criteria.add(d);
			}
		}
		
		// Ordernaciones, solo para recogida resultados y no para count()
		logger.debug("colOrderby = " + colOrderBy);
		if(applyOrder){
			if(applyOrdenation && colOrderBy!= null && !colOrderBy.equals("") ){
				if(colOrderDirection.equalsIgnoreCase("desc")){
					criteria.addOrder(Order.desc(colOrderBy));
				}else{
					criteria.addOrder(Order.asc(colOrderBy));
				}
			}
		}
		
		return criteria;
	}
	
	

	/**
	 * 
	 * @param request
	 * @param clazz
	 * @return
	 */
	public int getListViewCount(DataTablePaginationRequest request, 
			Class<? extends Serializable> clazz){
		String columnId = null;
		for(Field f : clazz.getDeclaredFields()){
			if(f.isAnnotationPresent(Id.class)){
				columnId = f.getName();
				return this.getListViewCount(request, clazz, columnId);
			}
			if(f.isAnnotationPresent(EmbeddedId.class)){
				return this.getListViewCount(request, clazz, null);
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * @param request
	 * @param clazz
	 * @param countDistinctParam
	 * @return
	 */
	public int getListViewCount(DataTablePaginationRequest request, 
			Class<? extends Serializable> clazz, String countDistinctParam){
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(clazz);
		try{
			if(countDistinctParam != null){
				criteria.setProjection(
					Projections.countDistinct(countDistinctParam)
				);
			}else{
				criteria.setProjection(
					Projections.rowCount()
				);
			}
			this.applyPaginationRequestFilters(criteria, request, clazz, false, clazz);
	
			int numeroRegistros = ((Long)criteria.uniqueResult()).intValue();
			logger.debug("Nº de registros a mostrar en listado:" + numeroRegistros);
			return numeroRegistros;
		}catch(Throwable t){
			logger.error("Error recovering total row count: " + t.getMessage(), t);
			return 0;
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param clazz
	 * @return
	 */
	public List<? extends Serializable> getListView(
			DataTablePaginationRequest request, 
			Class<? extends Serializable> clazz){
		
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(clazz);
		           
		try{
			this.applyPaginationRequestFilters(criteria, request, clazz, true, clazz);
			if(request.getNumero() == 0 && request.getPagina()== 0 ){
	            logger.debug("rama 1");
	            criteria.setFirstResult(0);
	            criteria.setMaxResults(15);
	        }else{
	            logger.debug("rama 2");
	            int firstResult = request.getPagina();
	            criteria.setFirstResult(firstResult);
	            logger.debug("request.criteria.getFirstResult: " + firstResult);
	            criteria.setMaxResults(request.getNumero());
	        }
			return criteria.list();
		}catch(Throwable t){
			logger.error("Error recovering rows: " + t.getMessage(), t);
			return null;
		}
		
	}
	
	/**
	 * 
	 * @param theField
	 * @param columnSearchableAnnotatedColumn
	 * @param criteriaRootClass
	 * @param filter
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException
	 */
	private Criterion generateCriterion(Field theField, String columnSearchableAnnotatedColumn, Class<? extends Serializable> criteriaRootClass, String filter) throws ClassNotFoundException, NoSuchFieldException{
		logger.debug("Filter = " + filter);
		logger.debug("columnSearchableAnnotatedColumn = " + columnSearchableAnnotatedColumn);
		logger.debug("criteriaRootClass = " + criteriaRootClass);
		logger.debug("theField = " + theField);
		if(theField != null){	
			//logger.debug("f.getType: " + theField.getType());
			//logger.debug("f.getType.isAssignableFrom: " + theField.getType().isAssignableFrom(Date.class));
			//logger.debug("f.getType==: " + (theField.getType() == Date.class));
			//logger.debug("f.getType.equals: " + theField.getType().equals(Date.class));
			for(String className: this.typeRestrictionGeneratorFactory.getRestrictionGenerators().keySet()){
				Class theClazz = Class.forName(className);
				if(theField.getType().isAssignableFrom(theClazz)){
					Criterion c = 
							this.typeRestrictionGeneratorFactory.getTypeRestrictionGenerator(className)
									.generateCriterion(
											theField, 
											columnSearchableAnnotatedColumn, 
											criteriaRootClass, 
											filter
										);
					return c;
					/*criteria.add(c);
					break;*/
				}else{
					// Primitive types!
					for(Field field : theClazz.getFields()){
						logger.debug("field name = " + field.getName());
						if(field.getName().equals("TYPE")){
							logger.debug(">>>>>>>> field TYPE found !!!!!!!!!!!!!!!");
							try{
								if(field.get(null).equals(theField.getType())){
									Criterion c = 
											this.typeRestrictionGeneratorFactory.getTypeRestrictionGenerator(className)
													.generateCriterion(
															theField, 
															columnSearchableAnnotatedColumn, 
															criteriaRootClass, 
															filter
														);
									return c;
									/*criteria.add(c);
									generatorFound = true;
									break;*/
								}
							}catch(Throwable t){
								logger.warn("Error " + t.getMessage());
							}
							//Class.forName(className).newInstance()												
						}
					}
				}
				
			}
		}
		return Restrictions.ilike(columnSearchableAnnotatedColumn, filter + "%");
		
	}

	public PrincipalSerializableIdLocator getPrincipalSerializableIdLocator() {
		return principalSerializableIdLocator;
	}

	public void setPrincipalSerializableIdLocator(PrincipalSerializableIdLocator principalSerializableIdLocator) {
		this.principalSerializableIdLocator = principalSerializableIdLocator;
	}

}
