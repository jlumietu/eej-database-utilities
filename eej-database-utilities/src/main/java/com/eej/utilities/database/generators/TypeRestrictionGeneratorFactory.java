/**
 * 
 */
package com.eej.utilities.database.generators;

import java.util.Map;

/**
 * @author doibalmi
 *
 */
public class TypeRestrictionGeneratorFactory {

	private Map<String, TypeRestrictionGenerator> restrictionGenerators;

	public TypeRestrictionGenerator getTypeRestrictionGenerator(String className){
		return this.restrictionGenerators.get(className);
	}
	
	/**
	 * @return the restrictionGenerators
	 */
	public Map<String, TypeRestrictionGenerator> getRestrictionGenerators() {
		return restrictionGenerators;
	}

	/**
	 * @param restrictionGenerators the restrictionGenerators to set
	 */
	public void setRestrictionGenerators(
			Map<String, TypeRestrictionGenerator> restrictionGenerators) {
		this.restrictionGenerators = restrictionGenerators;
	}
	
	
	
}
