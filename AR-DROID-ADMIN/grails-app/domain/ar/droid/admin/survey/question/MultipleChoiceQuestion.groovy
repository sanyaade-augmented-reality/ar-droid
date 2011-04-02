package ar.droid.admin.survey.question

import java.util.List;

import org.hibernate.mapping.Set;
import org.springframework.context.i18n.LocaleContextHolder;


class MultipleChoiceQuestion extends Question{
	Integer maxOptions
	def messageSource
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static hasMany = [options:Choice]
           	
    static constraints = {
		maxOptions(nullable:false)
    }
	
	public String getType(){
		def xValue =messageSource.getMessage("default.MultipleChoiceQuestion.type",null,"Multiple Choice",LocaleContextHolder.getLocale())
		return xValue;
	}
	
	public Integer maxOptions(){
		return maxOptions;
	}
	
	public void addOption(Choice choice){
		if (options == null)
			options = [];
		options.add(choice)
	}
	
	public java.util.Set getxxx(){
		return options
	}
		
	

}