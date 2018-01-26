package com.eej.utilities.database.generators.vendor.oracle;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.eej.utilities.database.generators.TypeRestrictionGenerator;
import com.eej.utilities.database.generators.vendor.mysql.MySqlDateTimeTypeRestrictionGenerator;

public class OracleDateTimeTypeRestrictionGenerator extends MySqlDateTimeTypeRestrictionGenerator 
		implements TypeRestrictionGenerator {
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	protected Criterion generateRestriction(String dataBaseFieldName, String conversionPattern, String filter) {
		return Restrictions.sqlRestriction(
					"to_char(" + dataBaseFieldName + " , '" 
					+ this.getNativeDatabaseConversionPattern(conversionPattern) + "') like '" 
					+ filter + "%'"
				);
	}

	@Override
	protected String getNativeDatabaseConversionPattern(String dateConversionPattern) {
		logger.debug("dateConversionPattern = " + dateConversionPattern);
		String timeConversionPattern = null;
		if(dateConversionPattern.contains(" ")){
			timeConversionPattern = dateConversionPattern.substring(dateConversionPattern.indexOf(" ")).trim();
			dateConversionPattern = dateConversionPattern.replace(timeConversionPattern, "").trim();
		}	
		
		String result = "";
		if(dateConversionPattern != null){
			String[] splitted = this.getDateConversionPatternSplitted(dateConversionPattern);
			logger.debug("dateConversionPattern, splitted = " + splitted.length);
			String separationChar = this.getDateSeparationChar(dateConversionPattern);
			logger.debug("dateConversionPattern, separationChar = " + separationChar);
			for(String s: splitted){
				logger.debug("splitted, part = " + s);
				if(!result.equals("")){
					result = result.concat(separationChar);
				}
				if(s.substring(0, 1).equalsIgnoreCase("y")){
					if(s.length() > 2){
						result = result.concat("yyyy");
					}else{
						result = result.concat("yy");
					}
				}else if(s.substring(0, 1).equals("M")){
					result = result.concat("mm");
				}else{
					result = result.concat(s);
				}
			}
			logger.debug("NativeDatabaseConversionPattern = " + result);
		}
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
					if(s.substring(0,1).equals("H")){
						result = result.concat("hh24");
					}else if(s.equals("mm")){
						result = result.concat("mi");
					}else{
						result = result.concat(s);
					}
				}
			}			
		}		
		logger.debug("NativeDatabaseConversionPattern = " + result);
		return result;
	}

	

	

}
