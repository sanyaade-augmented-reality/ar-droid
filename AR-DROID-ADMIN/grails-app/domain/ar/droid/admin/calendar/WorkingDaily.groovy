package ar.droid.admin.calendar

import java.util.Calendar

class WorkingDaily extends EventCalendar{

	static constraints = {
	}

	@Override
	public String toString() {
		return "WorkingDaily";
	}

	def isToday = {
		Date hoy = new Date()
		if(hoy.compareTo(this.startDate) >= 0)
			if(hoy.getAt(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && hoy.getAt(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
			return true
		else
			return false
	}

	def isWeekle = {
		Date hoy = new Date()
		if(hoy.compareTo(this.startDate) >= 0)
			return true
		else
			return false
	}

	def isMonthly = { return this.isWeekle() }
}