package ar.droid.admin

class Event {
	String title
	String description
	GeoPoint geoPoint;
	EventCalendar eventCalenar 
	static embedded = ['geoPoint']/**Esto indicar que el GeoPoint es una composici�n*/
	
	/**Conocimiento bidireccional con Activity y con la propiedad belongTo cada vez que se
	* elimine una Actividad se eliminan sus eventos*/
	static belongsTo = [activity:Activity]
	
	/**template encuenta*/
	SurveyTemplate surveyTemplate
	
	static constraints = {
    }
}

