package ar.droid.admin

import org.codehaus.groovy.grails.validation.BlankConstraint;

class Event {
	
	static hasMany = [activities: Activity]
	
	String title
	String description
	GeoPoint geoPoint
	EventCalendar eventCalenar
	byte[] photo
	static embedded = ['geoPoint'] /**Esto indicar que el GeoPoint es una composición*/
		
	/* template encuesta */
	SurveyTemplate surveyTemplate
	
	static constraints = {
		title(blank: false)
    }
}

