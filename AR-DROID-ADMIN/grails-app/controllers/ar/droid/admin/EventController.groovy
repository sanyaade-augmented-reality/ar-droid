package ar.droid.admin

import ar.droid.admin.calendar.*
import ar.droid.admin.survey.response.TextValueResponse
import ar.droid.admin.survey.response.SurveyResponse

class EventController {
	def eventService

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[eventInstanceList: Event.list(params), eventInstanceTotal: Event.count()]
	}

	def create = {
		def eventInstance = new Event()
		eventInstance.properties = params
		eventInstance.geoPoint = new GeoPoint()
		if(params.entity){
			eventInstance.entity.id = params.entity
		}
		return [eventInstance: eventInstance, activities: []]
	}

	def save = {
		def eventInstance = eventService.saveEvent(params)
		if(eventInstance.hasErrors()){
			request.eventCalendar_select = eventInstance.eventCalendar.class
			render(view: "create", model: [eventInstance: eventInstance])
		}
		else {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'event.label', default: 'Evento'), eventInstance.id])}"
			redirect(action: "show", id: eventInstance.id)
		}
	}

	def show = {
		def eventInstance = Event.get(params.id)
		if (!eventInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'event.label', default: 'Evento'), params.id])}"
			redirect(action: "list")
		}
		else {
			[eventInstance: eventInstance]
		}
	}

	def edit = {
		def eventInstance = Event.get(params.id)
		if (!eventInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'event.label', default: 'Evento'), params.id])}"
			redirect(action: "list")
		}
		else {
			request.eventCalendar_select = eventInstance.eventCalendar.class
			return [eventInstance: eventInstance, activities: eventInstance.activities]
		}
	}

	def update = {
		def eventInstance = eventService.updateEvent(params)
		if(eventInstance.hasErrors()){
			eventInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
				message(code: 'event.label', default: 'Evento')]
			as Object[], "Otro usuario modificó el objeto")
			render(view: "edit", model: [eventInstance: eventInstance])
		}
		else{
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'event.label', default: 'Evento'), eventInstance.id])}"
			redirect(action: "show", id: eventInstance.id)
		}
	}

	def delete = {
		def eventInstance = Event.get(params.id)
		if (eventInstance) {
			try {
				eventInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'event.label', default: 'Evento'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'event.label', default: 'Evento'), params.id])}"
				redirect(action: "show", id: params.id)eventInstance
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'event.label', default: 'Evento'), params.id])}"
			redirect(action: "list")
		}
	}

	def showImage ={
		def eventInstance = Event.get(params.id)
		response.contentType = "image/jpeg"
		response.contentLength = eventInstance?.photo.length
		response.outputStream.write(eventInstance?.photo)
	}

	def newactivity ={
		def eventInstance = Event.get(params.id)
		redirect(controller: "activity", action: "create", event: params.id)
	}

	def evento = {
		def eventInstance = Event.get(params.id)
		if (!eventInstance) {
			response.outputStream.write("NO existe el evento que quiere visualizar!")
		}
		else {
			eventInstance.clientVisits = (eventInstance.clientVisits + 1)
			eventInstance.save()
			[eventInstance: eventInstance]
		}
	}

	def comentarios = {
		def eventInstance = Event.get(params.id)
		if (!eventInstance) {
			response.outputStream.write("NO existe el evento que quiere visualizar!")
		}
		else {
			def commentarios = []
			for (SurveyResponse sr : eventInstance.responses) {
				TextValueResponse tvr = sr.first()
				commentarios.add(tvr)
			}

			[eventInstance: eventInstance, comentarios: commentarios]
		}
	}
}
