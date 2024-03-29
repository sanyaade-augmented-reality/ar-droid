package ar.droid.admin

import ar.droid.admin.calendar.EventCalendar
import org.springframework.core.io.ResourceLoader

class EventService {
	static transactional = true
	def servletContext

	def Event saveEvent(params) {
		def eventInstance = new Event()
		eventInstance.properties = params
		eventInstance.eventCalendar = EventCalendar.fromString(params.eventCalendar_select)

		// crear composiciones
		eventInstance.eventCalendar.properties = params.event.eventCalendar
		eventInstance.geoPoint = new GeoPoint()
		def canSave = false

		// agrego foto?
		if(params.get("photo") == null || params.get("photo").size == 0){
			def filePhoto = servletContext.getResource("/images/sin_imagen.jpg").file
			eventInstance.photo = filePhoto.getBytes()
		}

		// ubicación de la actividad
		if(params.positionSameAsEntity == "1"){
			def entity = Entity.get(params.entity.id)
			eventInstance.geoPoint = entity.geoPoint
			canSave = true
		}
		else{
			// validar posición
			if(params.latitude == null || ''.equals(params.latitude) || params.longitude == null || ''.equals(params.longitude)){
				eventInstance.errors.rejectValue('geoPoint', 'Debe seleccionar la ubicación')
			}
			else {
				// armar geopoint
				eventInstance.geoPoint.latitude = new BigDecimal(params.latitude)
				eventInstance.geoPoint.longitude = new BigDecimal(params.longitude)
				eventInstance.geoPoint.altitude = new BigDecimal(params.altitude)
				canSave = true
			}
		}
		if(canSave){
			eventInstance.webVisits = 0
			eventInstance.clientVisits = 0
			eventInstance.save(flush: true)
		}

		return eventInstance;
	}

	def Event updateEvent(params) {
		def eventInstance = Event.get(params.id)
		if (eventInstance){
			if (params.version) {
				def version = params.version.toLong()
				if (eventInstance.version > version) {
					eventInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'entity.label', default: 'Entity')]
					as Object[], "Another user has updated this Entity while you were editing")
					return entityInstance
				}
			}

			def canSave = false

			// ubicación de la actividad
			if(params.positionSameAsEntity == "1"){
				def entity = Entity.get(params.entity.id)
				eventInstance.geoPoint = entity.geoPoint
				canSave = true
			}
			else{
				// armar geopoint
				eventInstance.geoPoint.latitude = new BigDecimal(params.latitude)
				eventInstance.geoPoint.longitude = new BigDecimal(params.longitude)
				eventInstance.geoPoint.altitude = new BigDecimal(params.altitude)
			}
			def photo = eventInstance.photo;

			eventInstance.properties = params

			// crear composiciones
			eventInstance.eventCalendar = EventCalendar.fromString(params.eventCalendar_select)
			eventInstance.eventCalendar.properties = params.event.eventCalendar

			// verificar si cambiar o mantener imagen
			if(params.get("photo").size == 0){
				eventInstance.photo = photo;
			}
			eventInstance.save(flush: true)
		}
		return eventInstance
	}

	def saveResponse(survey) {
		survey.save(flush: true)
	}

	def events (params) {
		println params.id
		def listevents =[]
		for (Event event : Event.list()) {
			boolean ok = filterEvent(event,params.id)
			if (ok){
				listevents.add(event)
			}
		}
		return listevents
	}

	def filterEvent(event,token){
		boolean ok = false;
		if ("today".equals(token)){
			ok = event.eventCalendar.isToday();
		}
		else if ("week".equals(token)){
			ok = event.eventCalendar.isWeekle();
		}
		else if ("month".equals(token)){
			ok = event.eventCalendar.isMonthly();
		}
		return ok;
	}
}
