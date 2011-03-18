package ar.droid.admin

import ar.droid.admin.calendar.EventCalendar;
import ar.droid.admin.survay.SurveyTemplate;
import ar.droid.admin.survay.response.SurveyResponse;


class Event {
	
	static hasMany = [activities: Activity, responses: SurveyResponse]
	Entity entity
	
	static belongsTo = [entity: Entity]
	
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
	
	@Override
	public String toString() {
		return title
	}
}

