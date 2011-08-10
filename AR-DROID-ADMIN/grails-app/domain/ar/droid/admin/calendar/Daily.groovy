package ar.droid.admin.calendar

class Daily extends EventCalendar{
	
	static constraints = {
	}

	@Override
	public String toString() {
		return "Daily";
	}
	
	public boolean isToday(){
		Date hoy = new Date()
		if(hoy.compareTo(this.startDate) >= 0)
			return true
		else
			return false
	}
	
	public boolean isWeekle(){
		return this.isToday()
	}
	
	public boolean isMonthly(){
		return this.isToday()
	}
}