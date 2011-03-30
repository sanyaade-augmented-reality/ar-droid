package ar.droid.admin.calendar

import ar.droid.admin.Entity

class EventCalendar {
	Date startDate
	Date endDate
	Entity entity
	
	static belongsTo = [entity: Entity]
	
    static constraints = {
		startDate(nullable: false)
		endDate(nullable: false)
    }
	
	public static EventCalendar fromString(String keyValue) {
		def domainClass = keyValue.substring(6)
		return Class.forName(domainClass, true, new GroovyClassLoader()).newInstance()			
	}
}
