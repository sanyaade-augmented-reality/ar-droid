package ar.droid.admin.survey.question

import org.springframework.context.i18n.LocaleContextHolder;

class NumericValueQuestion extends Question{
	Integer limitTo
	Integer limitFrom
	
	def messageSource
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static constraints = {
		limitTo(nullable:false)
		limitFrom(nullable:false)
    }
	
	
	public String getType(){
		def xValue =messageSource.getMessage("default.NumericValueQuestion.type",null,"Numeric Value",LocaleContextHolder.getLocale())
		return xValue;
	}
	
	public Integer limitTo(){
		return limitTo;
	}
	
	public Integer limitFrom(){
		return limitFrom;
	}
	
}
