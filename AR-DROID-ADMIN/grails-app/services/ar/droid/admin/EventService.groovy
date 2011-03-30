package ar.droid.admin

import ar.droid.admin.calendar.EventCalendar

class EventService {	
	static transactional = true
	
	def Event saveEvent(params) {
		def eventInstance = new Event()
		eventInstance.properties = params
		eventInstance.eventCalendar = EventCalendar.fromString(params.readerActivity_select)
		
		// crear composiciones
		eventInstance.eventCalendar.properties = params.eventCalendar
		
		eventInstance.geoPoint = new GeoPoint()
		
		// validar posición
		if(params.latitude == null || ''.equals(params.latitude) || params.longitude == null || ''.equals(params.longitude)){
			eventInstance.errors.rejectValue('geoPoint', 'You must select a GeoPoint')
		}
		else {
			// armar geopoint
			eventInstance.geoPoint.latitude = new BigDecimal(params.latitude)
			eventInstance.geoPoint.longitude = new BigDecimal(params.longitude)
			
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
					eventInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'entity.label', default: 'Entity')] as Object[], "Another user has updated this Entity while you were editing")
					return entityInstance
				}
			}
			
			// verificar si cambiar o mantener imagen
			def photo = eventInstance.photo;
			eventInstance.properties = params
			eventInstance.readerActivity = ReaderActivity.fromString(params.readerActivity_select)
			entityInstance.readerNews = ReaderNews.fromString(params.readerNews_select)
			if(params.get("photo").size == 0){
				entityInstance.photo = photo;
			}
			eventInstance.save(flush: true)
		}
		return entityInstance
	}
}
