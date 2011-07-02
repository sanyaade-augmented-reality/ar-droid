package ar.droid.admin

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone
import ar.droid.util.UtilDate
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.web.context.request.RequestContextHolder

import ar.droid.admin.TypeEvent
import ar.droid.admin.calendar.*
import ar.droid.admin.survey.SurveyTemplate

class ReaderActivityService {	
	def facebookGraphService
	
	def synchronizeEventsFacebook(entityInstance, params){
		def jsonArray = facebookGraphService.api("method/events.get", params.facebookdata,["uid":entityInstance.readerActivity.parameter,"format":"json"],"GET","api")
		jsonArray.each {
			
			def d1 = new UtilDate().formatDateEventFacebook(it.start_time)
			def d2 = new UtilDate().formatDateEventFacebook(it.end_time)
			
			def ev1 = Event.findByEid(it.eid)
			if (ev1 == null){
				GeoPoint geoPoint = entityInstance.geoPoint
				if (it.venue.longitude){
					def latitude = new BigDecimal(it.venue.latitude)
					def longitude = new BigDecimal(it.venue.longitude)
					geoPoint = new GeoPoint(latitude: latitude, longitude: longitude, altitude: 0)
				}
				
				Event event = new Event(title: it.name, eid: it.eid, description: it.description, 
					place: it.location, typeEvent: TypeEvent.get(1), geoPoint: geoPoint, entity: entityInstance,
					surveyTemplate: SurveyTemplate.get(1), eventCalendar: new Daily(startDate: d1, endDate: d2))

				event.save(flush: true)
				println event.hasErrors()
			}
		} 		
	}
	
	def synchronizeEventsDroid(entityInstance, params){
		def calendarTypes = ["UNIQUE": Unique.class, "[DAILY]": Daily.class, "WEEKLY": Weekly.class, "MONTHLY": Monthly.class]
		def url = (entityInstance.readerActivity.parameter + '?last_time_synchronization=' + 
				entityInstance.ultimaActualizacion.getTime().toString() + "&last_synchronization_id=" + 
				entityInstance.ultimoId).toURL()
				
		def xmlContent = url.text
		
		def maxId = -1		
		def events = new XmlParser().parseText(xmlContent)
		
		events.event.each { event ->
			def id = event.'@id'
			if(id.toLong() > maxId)
				maxId = id.toLong()
				
			GeoPoint geoPoint = entityInstance.geoPoint
			if(events.geoPoint.size() > 1){
				geoPoint = new GeoPoint()
				geoPoint.latitude = event.geoPoint.latitude.text().toFloat()
				geoPoint.longitude = event.geoPoint.longitude.text().toFloat()
				geoPoint.altitude = 0
			}			
			
			EventCalendar calendar = calendarTypes.get(event.eventCalendar.@type.toString()).newInstance();
			calendar.startDate = new Date(event.eventCalendar.startDate.text().toLong())
			calendar.endDate = new Date(event.eventCalendar.endDate.text().toLong())
			if("WEEKLY".equals(event.eventCalendar.@type))
				calendar.dayOfWeek = event.eventCalendar.dayOfWeek.text()
			else if("MONTHLY".equals(event.eventCalendar.@type))
				calendar.dayOfMonth = event.eventCalendar.dayOfMonth.text()
			
			def file = new FileOutputStream(event.photo.text().tokenize("/")[-1])
			def photo = new BufferedOutputStream(file)
			photo << new URL(event.photo.text()).openStream()
			photo.close()
			file.close()
			def filePhoto = new File(event.photo.text().tokenize("/")[-1])
			def bytes = filePhoto.getBytes()
			filePhoto.delete()
			
			Event eventInstance = new Event(title: event.title.text(), description: event.description.text(), 
				place: event.place.text(), typeEvent: TypeEvent.get(event.typeEvent.text().toLong()), geoPoint: geoPoint, 
				entity: entityInstance,	surveyTemplate: SurveyTemplate.get(1), eventCalendar: calendar, 
				photo: bytes)
			eventInstance.save(flush: true)			
			
			eventInstance.activities = []
			event.activities.activity.each { activity ->
				GeoPoint geoPointActivity = eventInstance.geoPoint
				
				if(activity.geoPoint.size() > 1){
					geoPointActivity = new GeoPoint()
					geoPointActivity.latitude = activity.geoPoint.latitude.text().toFloat()
					geoPointActivity.longitude = activity.geoPoint.longitude.text().toFloat()
					geoPointActivity.altitude = 0
				}
				
				println "act " + activity.typeActivity.text().toLong() + "," + activity.description.text()
				Activity activityInstance = new Activity(name: activity.name.text(), geoPoint: geoPointActivity,
					description: activity.description.text(), typeActivity: TypeActivity.get(activity.typeActivity.text().toLong()),
					event: eventInstance)
				activityInstance.save(flush: true)
				println activityInstance.hasErrors()
				
				eventInstance.activities.add(activityInstance)
			}
			eventInstance.save(flush: true)
			println eventInstance.hasErrors()
		}
		
		entityInstance.ultimaActualizacion = new java.util.Date()
		entityInstance.ultimoId = maxId
	}
	
}
