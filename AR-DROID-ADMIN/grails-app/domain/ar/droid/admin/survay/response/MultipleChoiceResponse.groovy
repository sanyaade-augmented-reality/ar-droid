package ar.droid.admin.survay.response

import ar.droid.admin.survay.Choice;
import ar.droid.admin.survay.question.MultipleChoiceQuestion;

class MultipleChoiceResponse extends Response{

	static hasMany =[options:Choice]
	
	MultipleChoiceQuestion multipleChoiceQuestion
	
    static constraints = {
    }
	
	
}
