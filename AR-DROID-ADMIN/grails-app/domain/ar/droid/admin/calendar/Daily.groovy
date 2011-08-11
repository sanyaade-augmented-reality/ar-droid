package ar.droid.admin.calendar

class Daily extends EventCalendar{
	
	static constraints = {
	}

	@Override
	public String toString() {
		return "Daily";
	}
	
	def isToday = {
		Date hoy = new Date()
		if(hoy.compareTo(this.startDate) >= 0)
			return true
		else
			return false
	}
	
	def isWeekle = {
		return this.isToday()
	}
	
	def isMonthly = {
		return this.isToday()
	}
}