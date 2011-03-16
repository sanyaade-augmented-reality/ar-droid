package ar.droid.admin


class Activity {
	
	String name	
	String description
	GeoPoint geoPoint	
	TypeActivity typeActivity
	Entity entity
	
	static belongsTo = [entity: Entity]
	
	static mapping = { 
		description type: 'text'
	}
	static constraints = { 
		name(blank: false)
	}
	
	/**Esto indicar que el GeoPoint es una composici�n*/
	static embedded = ['geoPoint'] 
}
