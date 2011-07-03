package ar.droid.admin

import ar.droid.admin.survey.response.SurveyResponse

class StatsController {
    static allowedMethods = [show: "GET"]

    def show = {
		def eventInstance = Event.get(params.id)
		if(eventInstance != null){
			[eventInstance: eventInstance, question: eventInstance.surveyTemplate.first(), questions: eventInstance.surveyTemplate.questions, responses: eventInstance.responses]
		}
		else
			 redirect(controller: "event", action: "list")
	}
	
	def deleteResponse = {
		def responseInstance = SurveyResponse.get(params.id)
		if(responseInstance != null){
			responseInstance.delete()
		}
		redirect(controller: "stats", action: "show", params: params)
	}
}
