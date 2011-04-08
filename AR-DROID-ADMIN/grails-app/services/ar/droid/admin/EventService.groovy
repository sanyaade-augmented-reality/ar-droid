package ar.droid.admin

import java.text.SimpleDateFormat

import ar.droid.admin.DateUtils;
import ar.droid.admin.calendar.EventCalendar

class EventService {	
	static transactional = true
	
	def Event saveEvent(params) {
		def eventInstance = new Event()
		eventInstance.properties = params
		eventInstance.eventCalendar = EventCalendar.fromString(params.eventCalendar_select)
				
		// crear composiciones y agregar fechas
		eventInstance.eventCalendar.properties = params.eventCalendar
		eventInstance.eventCalendar.startDate = DateUtils.getDate(params.eventCalendar.startDate_day, params.eventCalendar.startDate_month, params.eventCalendar.startDate_year, params.eventCalendar.startDate_hour, params.eventCalendar.startDate_minute)
		eventInstance.eventCalendar.endDate = DateUtils.getDate(params.eventCalendar.endDate_day, params.eventCalendar.endDate_month, params.eventCalendar.endDate_year, params.eventCalendar.endDate_hour, params.eventCalendar.endDate_minute)
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
