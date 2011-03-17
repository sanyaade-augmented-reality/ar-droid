package ar.droid.admin

class SurveyTemplate {

	String description
	
	/** una plantilla tienen  N preguntas */
	static hasMany = [questions:Question]
		
	static constraints = {
		description(blank: false)
    }
}
