package ar.droid.admin.survey.response

import ar.droid.admin.Event

class SurveyResponse {
	String comment
	Event event
	
	static belongsTo = [event: Event]
	static hasMany = [responses: Response]
	
	static mapping = {
	}
	
	static constraints = {
		comment(nullable:true)
    }
}