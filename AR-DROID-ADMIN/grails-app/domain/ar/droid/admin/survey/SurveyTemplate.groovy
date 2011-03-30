package ar.droid.admin.survey

import ar.droid.admin.survey.question.Question;

class SurveyTemplate {

	String description
	
	/** una plantilla tienen  N preguntas */
	static hasMany = [questions: Question]
		
	static constraints = {
		description(blank: false)
    }
}
