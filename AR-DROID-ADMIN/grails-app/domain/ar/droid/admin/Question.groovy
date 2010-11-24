package ar.droid.admin

import org.hamcrest.core.IsNull;

class Question {

	String question
	
	static hasMany = [templates:SurveyTemplate]
	static belongsTo = SurveyTemplate
	
	static mapping ={
		tablePerHierarchy false
	}
	
	static constraints = {
		
    }
}
