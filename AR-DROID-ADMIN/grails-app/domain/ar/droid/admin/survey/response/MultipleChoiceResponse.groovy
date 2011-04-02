package ar.droid.admin.survey.response

import ar.droid.admin.survey.question.Choice;
import ar.droid.admin.survey.question.MultipleChoiceQuestion;

class MultipleChoiceResponse extends Response {
	MultipleChoiceQuestion multipleChoiceQuestion
	
	static hasMany =[options:Choice]
	
	static mapping = {
		tablePerHierarchy false
	}
	
    static constraints = {
		multipleChoiceQuestion(nullable:true)
    }
}
