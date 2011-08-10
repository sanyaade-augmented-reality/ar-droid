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
	
	public boolean isToday(){
		//Es para hoy si la fecha de inicio es hoy
		return (startDate.compareTo( new Date())==0)
	}
	
	public boolean isWeekle(){
		//es para esta semana si la fecha de inicio es 
		return true;
	}
	
	public boolean isMonthly(){
		return true;
	}
}