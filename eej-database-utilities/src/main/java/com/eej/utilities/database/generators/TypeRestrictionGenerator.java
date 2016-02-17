package com.eej.utilities.database.generators;

import java.lang.reflect.Field;

import org.hibernate.criterion.Criterion;

/**
 * 
 * @author jlumietu
 *
 */
public interface TypeRestrictionGenerator {

	Criterion generateCriterion(Field theField, String columnSearchableAnnotatedColumn, Class<?> criteriaRootClass, String filter) throws NoSuchFieldException;
	
}
