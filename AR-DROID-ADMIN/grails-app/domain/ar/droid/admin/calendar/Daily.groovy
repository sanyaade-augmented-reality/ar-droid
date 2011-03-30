package ar.droid.admin.calendar


class Daily extends RepeatCalendar{
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static constraints = {
	}

	@Override
	public String toString() {
		return "Daily";
	}
}