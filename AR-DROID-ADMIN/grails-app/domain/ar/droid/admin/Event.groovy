package ar.droid.admin

import ar.droid.admin.calendar.EventCalendar
import ar.droid.admin.survey.SurveyTemplate
import ar.droid.admin.survey.response.SurveyResponse

class Event {
	String title
	String description
	GeoPoint geoPoint
	EventCalendar eventCalendar
	byte[] photo
	TypeEvent typeEvent
	String eid
	SurveyTemplate surveyTemplate
	
	static hasMany = [activities: Activity, responses: SurveyResponse]
	static belongsTo = [entity: Entity]
	
	/**Esto indicar que el GeoPoint es una composición*/
	static embedded = ['geoPoint']
	
	static mapping = {
		description type: 'text'
		photo sqlType: 'blob' // para archivos grandes
	}
	
	static constraints = {
		title(blank: false)
		description(blank: false)
		eid(nullable:true)
		surveyTemplate(nullable:true)
		photo(nullable:true)
    }
	
	@Override
	public String toString() {
		return title
	}
}

