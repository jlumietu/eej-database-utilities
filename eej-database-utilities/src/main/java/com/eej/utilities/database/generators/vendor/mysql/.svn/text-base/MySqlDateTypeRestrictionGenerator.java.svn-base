package com.eej.utilities.database.generators.vendor.mysql;

import java.lang.reflect.Field;

import javax.persistence.Column;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.eej.utilities.annotation.DataTableColumn;
import com.eej.utilities.database.generators.TypeRestrictionGenerator;
import com.eej.utilities.model.SplittedStringDate;

/**
 * 
 * @author doibalmi
 *
 */
public class MySqlDateTypeRestrictionGenerator implements TypeRestrictionGenerator{

	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 
	 * @return
	 */
	public Criterion generateCriterion(Field theField, String columnSearchableAnnotatedColumn, Class<?> criteriaRootClass, String filter) throws NoSuchFieldException{
		Criterion criterion = null;
		DataTableColumn dtc = theField.getAnnotation(DataTableColumn.class);
		String filterDate = columnSearchableAnnotatedColumn;
		logger.debug("FilterDate " + filterDate);
		SplittedStringDate splitted = this.splitStringDate(filterDate, dtc.dateConversionPattern());
		
		Field pojoClassField = criteriaRootClass.getDeclaredField(columnSearchableAnnotatedColumn); 
		if(pojoClassField != null && pojoClassField.isAnnotationPresent(Column.class)){
			String dataBaseFieldName = pojoClassField.getAnnotation(Column.class).name();
			logger.debug("dataBaseFieldName = " + dataBaseFieldName );
			/*if(splitted.getDay()!=null){
				criteria.add(Restrictions.eq(propertyName, value))
			}*/
			criterion = Restrictions.sqlRestriction(
							"DATE_FORMAT(" + dataBaseFieldName + " , '" 
							+ this.getNativeDatabaseConversionPattern(dtc.dateConversionPattern()) + "') like '" 
							+ filter + "%'"
						);
				
		}
		return criterion;
	}
	
	/**
	 * 
	 * @param filterDate
	 * @param dateConversionPattern
	 * @return
	 */
	private SplittedStringDate splitStringDate(String filterDate,
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
	protected String getDateSeparationChar(String dateConversionPattern){
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
	protected String[] getDateConversionPatternSplitted(String dateConversionPattern){
		return dateConversionPattern.split(this.getDateSeparationChar(dateConversionPattern));
	}
	
	/**
	 * 
	 * @param dateConversionPattern
	 * @return
	 */
	protected String getNativeDatabaseConversionPattern(String dateConversionPattern){
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
			}else if(s.substring(0, 1).equals("M")){
				result = result.concat("%" + s.substring(0, 1).toLowerCase());
			}else{
				result = result.concat("%" + s.substring(0, 1));
			}
		}
		logger.debug("NativeDatabaseConversionPattern = " + result);
		return result;
	}
	
}
