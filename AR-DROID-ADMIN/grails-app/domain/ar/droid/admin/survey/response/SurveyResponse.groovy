package ar.droid.admin.survey.response

import ar.droid.admin.Event

class SurveyResponse {
	String comment
		
	static belongsTo = [event: Event]
	static hasMany = [responses: Response]
	
	static mapping = {
	}
	
	static constraints = {
		comment(nullable:true)
    }
	
	def createResponsesFromJSON(event,objetJson){
		responses =[]
		this.event = event	
		objetJson.responses.each {r->
			def domainClass = r.class
			def response = Class.forName(domainClass, true, new GroovyClassLoader()).newInstance()
			response.surveyResponse = this
			response.createResponseFromJSON(r);
			responses.add(response)
		}
	}
}