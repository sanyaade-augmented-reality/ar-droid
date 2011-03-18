package ar.droid.admin.calendar

import java.sql.Time;

 class EventCalendar {
	Date startDate;
	Date endDate
	Time startTime
	Time endTime
	
    static constraints = {
		startDate(nullable: false)
		endDate(nullable: false)
		startTime(nullable: false)
		endTime(nullable: false)
    }
}
