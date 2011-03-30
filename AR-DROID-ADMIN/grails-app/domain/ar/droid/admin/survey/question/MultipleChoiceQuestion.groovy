package ar.droid.admin.survey.question

import ar.droid.admin.survey.Choice;

class MultipleChoiceQuestion extends Question{
	Integer maxOptions
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static hasMany = [options:Choice]
           	
    static constraints = {
		maxOptions(nullable:true)
    }
}