package ar.droid.admin

import ar.droid.admin.calendar.EventCalendar

class EventService {	
	static transactional = true
	
	def Event saveEvent(params) {
		def eventInstance = new Event()
		eventInstance.properties = params
		eventInstance.eventCalendar = EventCalendar.fromString(params.eventCalendar_select)
		
		// crear composiciones
		eventInstance.eventCalendar.properties = params.event.eventCalendar
		eventInstance.geoPoint = new GeoPoint()
		def canSave = false
		
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
				canSave = true
			}
		}
		if(canSave)
			eventInstance.save(flush: true)
			
		return eventInstance;
	}
	
	def Event updateEvent(params) {
		def eventInstance = Event.get(params.id)
		if (eventInstance){
			if (params.version) {
				def version = params.version.toLong()
				if (eventInstance.version > version) {
					eventInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'entity.label', default: 'Entity')] as Object[], "Another user has updated this Entity while you were editing")
					return entityInstance
				}
			}
			
			def canSave = false
			
			println params.positionSameAsEntity
			println params._positionSameAsEntity
			
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
			}
			
			// crear composiciones
			eventInstance.eventCalendar.properties = params.event.eventCalendar
			
			// verificar si cambiar o mantener imagen
			def photo = eventInstance.photo;
			eventInstance.properties = params
			if(params.get("photo").size == 0){
				eventInstance.photo = photo;
			}
			eventInstance.save(flush: true)
		}
		return eventInstance
	}
}
