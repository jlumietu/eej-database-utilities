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
 * @author jlumietu - Mikel Ibiricu Alfaro
 *
 */
public class BooleanTypeRestrictionGenerator implements TypeRestrictionGenerator{
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(this.getClass());

	public Criterion generateCriterion(Field theField,
			String columnSearchableAnnotatedColumn, Class<?> criteriaRootClass,
			String filter) throws NoSuchFieldException {
		Criterion criterion = null;
		Field pojoClassField = criteriaRootClass.getDeclaredField(columnSearchableAnnotatedColumn); 
		if(pojoClassField != null && pojoClassField.isAnnotationPresent(Column.class)){
			boolean matches = false;
			boolean state = false;
			if("true".contains(filter)){
				state = true;
				matches = true;
			}else if("false".contains(filter)){
				state = false;
				matches = true;
			}
			if(matches){
				criterion = Restrictions.eq(pojoClassField.getName(), state);
			}
		}
		return criterion;
	}

}
