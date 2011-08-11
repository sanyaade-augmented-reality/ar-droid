package ar.droid.admin.survey.question

import java.util.List

import org.hibernate.mapping.Set


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
		return "Múltiples opciones"
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