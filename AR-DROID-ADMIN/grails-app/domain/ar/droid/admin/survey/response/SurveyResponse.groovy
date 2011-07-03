package ar.droid.admin.survey.response

import java.util.Date
import ar.droid.admin.Event

class SurveyResponse {
	Date fecha
	Event event
		
	static belongsTo = [event: Event]
	static hasMany = [responses: Response]
	
	def createResponsesFromJSON(event,objetJson){
		responses = []
		this.event = event	
		this.fecha = new Date()
		objetJson.responses.each {r->
			def domainClass = r.class
			def response = Class.forName(domainClass, true, new GroovyClassLoader()).newInstance()
			response.surveyResponse = this
			response.createResponseFromJSON(r);
			responses.add(response)
		}
	}
	
	Response first() {
		def res
		for(r in responses){
			res = r
			break
		}
		res
	}
}