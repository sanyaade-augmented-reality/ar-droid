package ar.droid.admin

import ar.droid.admin.survey.response.SurveyResponse
import grails.converters.JSON

class RequestController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def eventService
	
	
	def typeEntities = {
		render TypeEntity.list() as JSON
	}
	
	def typeActivities = {
		render TypeActivity.list() as JSON
	}

    def typeEvents = {
		render TypeEvent.list() as JSON
	}

    def entities = {
		render Entity.list() as JSON
	}
	
	def events = {
		def entity = Entity.get(params.id)
		render entity.activeEvents() as JSON 
	}
	
	def activities = {
		def event = Event.get(params.id)
		render event.activities as JSON 
	}
		
	def allevents = {
		//tomar parametro y filtro
		//render Event.list() as JSON
		render eventService.events(params) as JSON
	}
	
	def imageEntity ={
		def entityInstance = Entity.get(params.id)
		response.contentType = "image/jpeg"
		response.contentLength = entityInstance?.photo.length
		response.outputStream.write(entityInstance?.photo)
	}
	
	def iconTypeEntity = {
		def typeEntityInstance = TypeEntity.get(params.id)
		response.contentType = "image/jpeg"
		response.contentLength = typeEntityInstance?.icon.length
		response.outputStream.write(typeEntityInstance?.icon)
	}
	
	def imageEvent ={
		def eventInstance = Event.get(params.id)
		response.contentType = "image/jpeg"
		response.contentLength = eventInstance?.photo.length
		response.outputStream.write(eventInstance?.photo)
	}
	
	def feedback ={
		def json = request.JSON
		def event = Event.get(json.event.id)	
		
		println json.toString()
		if (!event.hasErrors()){
			def survey = new SurveyResponse()
			
			survey.createResponsesFromJSON(event,json)
			
			if (!survey.hasErrors())
				eventService.saveResponse(survey)
			
			//si da error notificar
		}
		
		//si da error notificar
		
	}
	
	
	def summaryFeedbak = {
		def event = Event.get(params.id);
		def result = ["rating": -1, "description":"(0 votos)", "comments":[]];
		if(event != null && event.surveyTemplate.first() != null)
			result = event.surveyTemplate.first().getSummary(event.responses)
		render result as JSON;	
	}
	
	
	def clientVisit = {
		def json = request.JSON
		def eventInstance = Event.get(json.id)
		if (!eventInstance.hasErrors()){
			eventInstance.clientVisits = (eventInstance.clientVisits + 1)
			eventInstance.save(flush: true)
		}
	}
	
	def searchEvents = {
		def text = params.text.trim()
		def results = Event.findAll("from Event as e where (e.title like ? or e.description like ?)", ["%" + text + "%", "%" + text + "%"])
		render results as JSON;	
	}
	
}
