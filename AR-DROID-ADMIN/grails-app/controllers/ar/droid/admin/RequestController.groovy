package ar.droid.admin

import ar.droid.admin.survey.response.SurveyResponse;
import grails.converters.JSON

class RequestController {
	
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
		def entity = Entity.get(params.id);
		render entity.events as JSON 
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
				survey.save()
			
			//si da error notificar
		}
		
		//si da error notificar
		
	}
	
	def clientVisit = {
		def json = request.JSON
		def eventInstance = Event.get(json.event.id)
		if (!eventInstance.hasErrors()){
			eventInstance.clientVisits = (eventInstance.clientVisits + 1)
			eventInstance.save()
		}
	}
	
	
}
