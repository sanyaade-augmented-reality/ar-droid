package ar.droid.admin

class Activity {
	
	String name	
	String description
	TypeActivity typeActivity
		
	static belongsTo = [event: Event]
	
	static mapping = { 
		description type: 'text'
	}
	static constraints = { 
		name(blank: false)
		description(blank: false)
	}
	
	@Override
	public String toString() {
		return name
	}
}
