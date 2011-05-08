package ar.droid.admin.survey.question

import org.springframework.context.i18n.LocaleContextHolder;

class TextValueQuestion extends Question {
	
	static mapping = {
		tablePerHierarchy false
	}
	
    static constraints = {
    }

	public String getType(){
		return messageSource.getMessage("default.TextValueQuestion.type",null,"Text",LocaleContextHolder.getLocale()) 
	}
	
}
