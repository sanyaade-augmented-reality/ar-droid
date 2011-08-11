package ar.droid.admin.calendar

import java.sql.Time
import ar.droid.admin.Event

class EventCalendar {
	Date startDate
	Date endDate
	
	static belongsTo = [event: Event]
	
    static constraints = {
		startDate(nullable: false)
		endDate(nullable: false)
	}
	
	public static EventCalendar fromString(String keyValue) {
		def domainClass = keyValue.substring(6)
		return Class.forName(domainClass, true, new GroovyClassLoader()).newInstance()			
	}
	
	def isToday = {
		false
	}
	
	def isWeekle = {
		false
	}
	
	def isMonthly = {
		false
	}
}
