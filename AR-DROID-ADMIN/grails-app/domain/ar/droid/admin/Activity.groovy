package ar.droid.admin

class Activity {
	
	String name	
	String description
	GeoPoint geoPoint	
	TypeActivity typeActivity
		
	static belongsTo = [event: Event]
	
	static mapping = { 
		description type: 'text'
	}
	static constraints = { 
		name(blank: false)
		geoPoint(nullable: true)
	}
	
	/**Esto indicar que el GeoPoint es una composición*/
	static embedded = ['geoPoint']
	
	@Override
	public String toString() {
		return name
	}
}
