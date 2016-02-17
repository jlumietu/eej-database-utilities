/**
 * 
 */
package com.eej.utilities.database.generators.generic;

import java.lang.reflect.Field;

import javax.persistence.Column;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.eej.utilities.database.generators.TypeRestrictionGenerator;

/**
 * @author jlumietu
 *
 */
public class IntegerEqualTypeRestrictionGenerator implements TypeRestrictionGenerator{
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public Criterion generateCriterion(Field theField,
			String columnSearchableAnnotatedColumn, Class<?> criteriaRootClass,
			String filter) throws NoSuchFieldException {
		logger.debug("columnSearchableAnnotatedColumn = " + columnSearchableAnnotatedColumn);
		logger.debug("theField = " + theField);
		logger.debug("criteriaRootClass = " + criteriaRootClass);
		logger.debug("filter = " + filter);
		Criterion criterion = null;
		Field pojoClassField = criteriaRootClass.getDeclaredField(columnSearchableAnnotatedColumn); 
		if(pojoClassField != null && pojoClassField.isAnnotationPresent(Column.class)){
			boolean matches = false;
			boolean state = false;
			try{
				criterion = Restrictions.eq(pojoClassField.getName(), Integer.parseInt(filter));
			}catch(Throwable t){
				logger.error("Error converting Integer value: " + t.getMessage(), t);
			}
		}
		return criterion;
	}
	

}
