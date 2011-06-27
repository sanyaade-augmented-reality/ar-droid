package ar.droid.admin

import ar.droid.admin.survey.response.Response

class StatsController {
    static allowedMethods = [show: "GET"]

    def show = {
		def eventInstance = Event.get(params.id)
		if(!eventInstance.hasErrors()){
			[eventInstance: eventInstance, question: eventInstance.surveyTemplate.first(), questions: eventInstance.surveyTemplate.questions, responses: eventInstance.responses]
		}
		else
			 redirect(controller: "event", action: "list")
	}
	
	def deleteResponse = {
		def responseInstance = Response.get(params.id)
		if(!responseInstance.hasErrors()){
			Response.delete(params.id)
		}
		redirect(controller: "stats", action: "show", params: params)
	}
}
