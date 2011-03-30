package ar.droid.admin.survey.response

import ar.droid.admin.survey.question.TextValueQuestion;

class TextValueResponse extends Response {
	String value	
	TextValueQuestion textValueQuestion
	
	static mapping = {
		tablePerHierarchy false
	}
	
    static constraints = {
		value(nullable:true)
		textValueQuestion(nullable:true)
    }
}
