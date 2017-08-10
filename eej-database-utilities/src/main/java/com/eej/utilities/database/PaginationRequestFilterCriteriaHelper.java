/**
 * 
 */
package com.eej.utilities.database;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Column;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.eej.utilities.annotation.DataTable;
import com.eej.utilities.annotation.DataTableColumn;
import com.eej.utilities.model.DataTablePaginationRequest;
import com.eej.utilities.model.SplittedStringDate;

/**
 * @author DOIBALMI
 *
 */
public class PaginationRequestFilterCriteriaHelper {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
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
							if(theField != null){
								logger.debug("f.getType: " + theField.getType());
								logger.debug("f.getType.isAssignableFrom: " + theField.getType().isAssignableFrom(Date.class));
								logger.debug("f.getType==: " + (theField.getType() == Date.class));
								logger.debug("f.getType.equals: " + theField.getType().equals(Date.class));
							}
							if(theField != null && theField.getType().isAssignableFrom(Date.class)){
								if(theField.isAnnotationPresent(DataTableColumn.class)){
									DataTableColumn dtc = theField.getAnnotation(DataTableColumn.class);
									String filterDate = columnSearchableAnnotatedColumn.get(s);
									logger.debug("FilterDate " + filterDate);
									//SplittedStringDate splitted = this.splitStringDate(filterDate, dtc.dateConversionPattern());
									logger.debug("criteria.alias = " + criteria.getAlias());
									logger.debug("criteria.alias = " + Criteria.ROOT_ENTITY);
									Field pojoClassField = criteriaRootClass.getDeclaredField(columnSearchableAnnotatedColumn.get(s)); 
									if(pojoClassField != null && pojoClassField.isAnnotationPresent(Column.class)){
										String dataBaseFieldName = pojoClassField.getAnnotation(Column.class).name();
										logger.debug("dataBaseFieldName = " + dataBaseFieldName );
										/*if(splitted.getDay()!=null){
											criteria.add(Restrictions.eq(propertyName, value))
										}*/
										criteria.add(
												Restrictions.sqlRestriction(
														"DATE_FORMAT(" + dataBaseFieldName + " , '" 
														+ this.getNativeDatabaseConversionPattern(dtc.dateConversionPattern()) + "') like '" 
														+ filter + "%'"
													)
											);
									}
								}
							}else{
								criteria.add(Restrictions.ilike(columnSearchableAnnotatedColumn.get(s), filter + "%"));
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
			for(String s : generalSearchableColumn){
				d.add(Restrictions.ilike(s, searchGeneral.concat("%")));
			}
			criteria.add(d);
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
	 * @param filterDate
	 * @param dateConversionPattern
	 * @return
	 */
	protected SplittedStringDate splitStringDate(String filterDate,
			String dateConversionPattern) {
		SplittedStringDate ssp = new SplittedStringDate();
		String breakChar = "/";
		if(dateConversionPattern.contains("-") && !dateConversionPattern.contains("/")){
			breakChar = "-";
		}
		String[] pieces = filterDate.split(breakChar);
		String[] patterns = dateConversionPattern.split(breakChar);
 		for(int i= 0; i<patterns.length; i++){
 			if(pieces.length > i){
 				if(patterns[i].equals("dd")){
 					ssp.setDay(pieces[i]);
 				}else if (patterns[i].equals("MM")){
 					ssp.setMonth(pieces[i]);
 				}else if (patterns[i].equals("yy") || patterns[i].equals("yyyy")){
 					ssp.setYear(pieces[i]);
 				}
 			}	
 		}
		return ssp;
	}
	
	/**
	 * 
	 * @param dateConversionPattern
	 * @return
	 */
	private String getDateSeparationChar(String dateConversionPattern){
		String breakChar = "/";
		if(dateConversionPattern.contains("-") && !dateConversionPattern.contains("/")){
			breakChar = "-";
		}
		return breakChar;
	}
	
	/**
	 * 
	 * @param dateConversionPattern
	 * @return
	 */
	private String[] getDateConversionPatternSplitted(String dateConversionPattern){
		return dateConversionPattern.split(this.getDateSeparationChar(dateConversionPattern));
	}
	
	/**
	 * 
	 * @param dateConversionPattern
	 * @return
	 */
	private String getNativeDatabaseConversionPattern(String dateConversionPattern){
		logger.debug("dateConversionPattern = " + dateConversionPattern);
		String[] splitted = this.getDateConversionPatternSplitted(dateConversionPattern);
		logger.debug("dateConversionPattern, splitted = " + splitted.length);
		String separationChar = this.getDateSeparationChar(dateConversionPattern);
		logger.debug("dateConversionPattern, separationChar = " + separationChar);
		String result = "";
		for(String s: splitted){
			logger.debug("splitted, part = " + s);
			if(!result.equals("")){
				result = result.concat(separationChar);
			}
			if(s.substring(0, 1).equalsIgnoreCase("y") && s.length() > 2){
				result = result.concat("%" + s.substring(0, 1).toUpperCase());
			}else{
				result = result.concat("%" + s.substring(0, 1));
			}
		}
		logger.debug("NativeDatabaseConversionPattern = " + result);
		return result;
	}

}
