package ar.droid.admin.survey.question

import org.springframework.context.i18n.LocaleContextHolder;

class NumericValueQuestion extends Question{
	Integer limitTo
	Integer limitFrom
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static constraints = {
		limitTo(nullable: true)
		limitFrom(nullable: true)
    }
	
	public String getType(){
		return "Valor numerico"
	}
	
	public Integer limitTo(){
		return limitTo;
	}
	
	public Integer limitFrom(){
		return limitFrom;
	}
	
}
