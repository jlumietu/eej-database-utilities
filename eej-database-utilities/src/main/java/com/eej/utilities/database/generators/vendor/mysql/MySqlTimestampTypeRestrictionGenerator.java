/**
 * 
 */
package com.eej.utilities.database.generators.vendor.mysql;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.eej.utilities.database.generators.TypeRestrictionGenerator;

/**
 * @author jlumietu - Mikel Ibiricu Alfaro
 *
 */
public class MySqlTimestampTypeRestrictionGenerator implements TypeRestrictionGenerator{
	
	private Logger logger = Logger.getLogger(this.getClass()); 

	@Override
	public Criterion generateCriterion(Field theField, String columnSearchableAnnotatedColumn,
			Class<?> criteriaRootClass, String filter) throws NoSuchFieldException {
		Criterion criterion = null;
		logger.debug("columnSearchableAnnotatedColumn " + columnSearchableAnnotatedColumn);
		logger.debug("value " + filter );
		Field pojoClassField = criteriaRootClass.getDeclaredField(columnSearchableAnnotatedColumn); 
		if(pojoClassField != null && (pojoClassField.isAnnotationPresent(Column.class) || pojoClassField.isAnnotationPresent(Id.class))){
			try{
				criterion = Restrictions.like(pojoClassField.getName(), filter + "%");
			}catch(Throwable t){
				logger.error("Error converting Integer value: " + t.getMessage(), t);
			}
		}
		return criterion;
	}

}
