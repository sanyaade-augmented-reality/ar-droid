package ar.droid.admin.survey.question

import org.springframework.context.i18n.LocaleContextHolder;

class NumericValueQuestion extends Question{
	Integer limitTo
	Integer limitFrom
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static constraints = {
		limitTo(nullable:false)
		limitFrom(nullable:false)
    }
	
	public String getType(){
		return "Valor num�rico"
	}
	
	public Integer limitTo(){
		return limitTo;
	}
	
	public Integer limitFrom(){
		return limitFrom;
	}
	
}
