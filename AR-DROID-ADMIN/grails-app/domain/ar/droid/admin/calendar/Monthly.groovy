package ar.droid.admin.calendar

class Monthly extends RepeatCalendar {
	Integer dayOfMonth
	
    static constraints = {
		dayOfMonth(range:1..31, nullable:true)
    }
	
	@Override
	public String toString() {
		return "Monthly";
	}
	
	public boolean isToday(){
		Date hoy = new Date()
		def diaMes = hoy.getAt(Calendar.DAY_OF_MONTH)
		if(hoy.compareTo(this.startDate) >= 0 && diaMes == this.dayOfMonth)
			return true
		else
			return false
	}
	
	public boolean isWeekle(){
		Date hoy = new Date()
		def diaMes = hoy.getAt(Calendar.DAY_OF_MONTH)
		if(hoy.compareTo(this.startDate) < 0)
			return false
		else {
			if (diaMes <= this.dayOfMonth)
				return ((this.dayOfMonth - diaMes) < 7)
			else {
				diaMes -= 30
				return ((this.dayOfMonth - diaMes) < 7)
			}
		}
	}
	
	public boolean isMonthly(){
		Date hoy = new Date()
		if(hoy.compareTo(this.startDate) >= 0)
			return true
	}
}
