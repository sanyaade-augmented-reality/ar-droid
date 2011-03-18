package ar.droid.admin.survay.question

import org.hamcrest.core.IsNull;

import ar.droid.admin.survay.SurveyTemplate;



class Question {

	String question
	
	static hasMany = [templates: SurveyTemplate]
	static belongsTo = SurveyTemplate
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static constraints = {
		question(blank: false)
    }
}
