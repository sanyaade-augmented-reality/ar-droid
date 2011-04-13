package ar.droid.admin

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import ar.droid.util.UtilDate;
import org.codehaus.groovy.grails.web.json.JSONArray;
import org.springframework.web.context.request.RequestContextHolder;

import ar.droid.admin.calendar.Daily;
import ar.droid.admin.calendar.EventCalendar;
import ar.droid.admin.survey.SurveyTemplate;

class readerActivityService {
	
	def facebookGraphService
	
	def synchronizeEventsFacebook(entityInstance,params){
		def jsonArray =facebookGraphService.api("method/events.get", params.facebookdata,["uid":entityInstance.readerActivity.parameter,"format":"json"],"GET","api")
		jsonArray.each {
			
			def d1 = new UtilDate().formatDateEventFacebook(it.start_time)
			def d2 = new UtilDate().formatDateEventFacebook(it.end_time)
			
			def ev1 = Event.findByEid(it.eid)
			if (ev1 == null){
				GeoPoint geoPoint =entityInstance.geoPoint
				if (it.venue.longitude){
					def latitude = new BigDecimal(it.venue.latitude)
					def longitude = new BigDecimal(it.venue.longitude)
					geoPoint = new GeoPoint(latitude:latitude,longitude:longitude)
				}
			
				TypeEvent typeEvent = TypeEvent.get(1)//ninguno
				SurveyTemplate surveyTemplate = SurveyTemplate.get(1)
				Event event = new Event(title:it.name,eid:it.eid,description:it.description,typeEvent:typeEvent,geoPoint:geoPoint,entity:entityInstance)
				Daily daily = new Daily(startDate:d1,endDate:d2)
				daily.event = event
				event.eventCalendar = daily
				event.save(flush: true)
				println event.hasErrors();
			}
		} 		
	} 
	
	
	def synchronizeEventsDroid(entityInstance){
		//implementar
	}
	
}
