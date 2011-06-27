package ar.droid.admin.survey.question

import java.util.List;

import org.hibernate.mapping.Set;
import org.springframework.context.i18n.LocaleContextHolder;


class MultipleChoiceQuestion extends Question{
	Integer maxOptions
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static hasMany = [options:Choice]
           	
    static constraints = {
		maxOptions(nullable: true)
    }
	
	public String getType(){
		return "Multiples opciones"
	}
	
	public Integer maxOptions(){
		return maxOptions;
	}
	
	public void addOption(Choice choice){
		if (options == null)
			options = [];
		options.add(choice)
	}
	
	public java.util.Set getOptionsQuestion(){
		return options
	}
		
	

}