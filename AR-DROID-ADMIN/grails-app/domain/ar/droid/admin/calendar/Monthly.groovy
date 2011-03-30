package ar.droid.admin.calendar

class Monthly extends RepeatCalendar {
	Integer dayOfMonth;
	
	static mapping = {
		tablePerHierarchy false
	}
	
    static constraints = {
		dayOfMonth(range:1..31, nullable:true);
    }
	
	@Override
	public String toString() {
		return "Monthly";
	}
}
