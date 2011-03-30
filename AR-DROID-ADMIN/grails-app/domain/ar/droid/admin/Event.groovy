package ar.droid.admin

import ar.droid.admin.calendar.EventCalendar
import ar.droid.admin.survey.SurveyTemplate
import ar.droid.admin.survey.response.SurveyResponse

class Event {
	String title
	Entity entity
	String description
	GeoPoint geoPoint
	EventCalendar eventCalendar
	byte[] photo
	TypeEvent typeEvent
	SurveyTemplate surveyTemplate
	
	static hasMany = [activities: Activity, responses: SurveyResponse]
	static belongsTo = [entity: Entity]
	
	/**Esto indicar que el GeoPoint es una composición*/
	static embedded = ['geoPoint']
	
	static constraints = {
		title(blank: false)
		description(blank: false)
    }
	
	@Override
	public String toString() {
		return title
	}
}

