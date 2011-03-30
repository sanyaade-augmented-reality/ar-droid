package ar.droid.admin.calendar


class Unique extends EventCalendar{
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static constraints = {
	}
	
	@Override
	public String toString() {
		return "Unique";
	}
}