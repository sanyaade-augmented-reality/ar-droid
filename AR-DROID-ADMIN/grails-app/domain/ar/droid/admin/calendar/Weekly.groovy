package ar.droid.admin.calendar

class Weekly extends RepeatCalendar {
	Integer dayOfWeek
	

	static constraints = {
		dayOfWeek(nullable:true)
    }
	
	@Override
	public String toString() {
		return "Weekly";
	}
	
	def isToday = {
		Date hoy = new Date()
		def diaSemana = hoy.getAt(Calendar.DAY_OF_WEEK)
		if(hoy.compareTo(this.startDate) >= 0 && diaSemana == dayOfWeek)
			return true
		else
			return false
	}
	
	def isWeekle = {
		Date hoy = new Date()
		if(hoy.compareTo(this.startDate) >= 0)
			return true
	}
	
	def isMonthly = {
		Date hoy = new Date()
		if(hoy.compareTo(this.startDate) >= 0)
			return true
	}
}
