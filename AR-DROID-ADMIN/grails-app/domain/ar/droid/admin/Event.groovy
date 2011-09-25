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
	String place
	
	int webVisits = 0
	int clientVisits = 0
	
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
		photo(nullable:true, size:0..5000000)
    }
	
	@Override
	public String toString() {
		return title
	}
	
	def isToday = {
		this.eventCalendar.isToday()
	}
	
	def isWeekle = {
		this.eventCalendar.isWeekle()
	}
	
	def isMonthly = {
		this.eventCalendar.isMonthly()
	}
}

