package ar.droid.admin

class Event {
	String title
	String description
	GeoPoint geoPoint;
	EventCalenar eventCalenar 
	static embedded = ['geoPoint']/**Esto indicar que el GeoPoint es una composición*/
	
	/**Conocimiento bidireccional con Activity y con la propiedad belongTo cada vez que se
	* elimine una Actividad se eliminan sus eventos*/
	static belongsTo = [activity:Activity]
	
	/**template encuenta*/
	SurveyTemplate surveyTemplate
	
	static constraints = {
    }
}

