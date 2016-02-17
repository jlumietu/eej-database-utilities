/**
 * 
 */
package com.eej.utilities.database.generators.vendor.mysql;

import org.apache.log4j.Logger;

import com.eej.utilities.database.generators.TypeRestrictionGenerator;

/**
 * @author jlumietu
 *
 */
public class MySqlDateTimeTypeRestrictionGenerator extends
		MySqlDateTypeRestrictionGenerator implements TypeRestrictionGenerator{

	private Logger logger = Logger.getLogger(this.getClass());
	
	/* (non-Javadoc)
	 * @see com.ib.utilities.database.generators.vendor.mysql.MySqlDateTypeRestrictionGenerator#getNativeDatabaseConversionPattern(java.lang.String)
	 */
	@Override
	protected String getNativeDatabaseConversionPattern(
			String dateConversionPattern) {
		logger.debug("dateConversionPattern = " + dateConversionPattern);
		String timeConversionPattern = null;
		if(dateConversionPattern.contains(" ")){
			timeConversionPattern = dateConversionPattern.substring(dateConversionPattern.indexOf(" ")).trim();
			dateConversionPattern = dateConversionPattern.replace(timeConversionPattern, "").trim();
		}		
		String result = super.getNativeDatabaseConversionPattern(dateConversionPattern);
		if(timeConversionPattern != null){
			String timeSeparationChar = this.getTimeSeparationChar(timeConversionPattern);
			String[] splitted = timeConversionPattern.split(timeSeparationChar);
			if(splitted.length > 0){
				boolean firstTimePattern = true;
				result = result.concat(" ");
				for(String s: splitted){
					logger.debug("splitted, part = " + s);
					if(firstTimePattern){
						firstTimePattern = false;
					}else{
						result = result.concat(timeSeparationChar);
					}
					if(s.substring(0, 1).equals("m")){
						result = result.concat("%i");
					}else{
						result = result.concat("%" + s.substring(0, 1));
					}
				}
			}			
		}		
		logger.debug("NativeDatabaseConversionPattern = " + result);
		return result;
	}
	
	protected String getTimeSeparationChar(String timeConversionPattern){
		String separationChar = ":";
		if(timeConversionPattern.contains(".") && !timeConversionPattern.contains(separationChar)){
			separationChar = ".";
		}
		return separationChar;
	}
	
	

}
