package ar.droid.admin.calendar

class Weekly extends RepeatCalendar {
	Integer dayOfWeek;
	

	static constraints = {
		dayOfWeek(nullable:true)
    }
	
	@Override
	public String toString() {
		return "Weekly";
	}
}
