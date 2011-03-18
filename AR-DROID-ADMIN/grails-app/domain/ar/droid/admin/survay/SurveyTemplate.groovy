package ar.droid.admin.survay

import ar.droid.admin.survay.question.Question;

class SurveyTemplate {

	String description
	
	/** una plantilla tienen  N preguntas */
	static hasMany = [questions:Question]
		
	static constraints = {
		description(blank: false)
    }
}
