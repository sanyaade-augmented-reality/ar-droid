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
	
	def isToday = {
		//Es para hoy si la fecha de inicio es hoy
		//o si ya empezo y la fecha de inicio es mayor igual a hoy
		Date today = new Date()
		boolean ok = (today.compareTo(startDate)==0);
		ok = ok || (startDate.before(today) && !endDate.before(today));
		return  ok;
	}
	
	def isWeekle = {
		//es para esta semana si la fecha de inicio es
		Date today = new Date()
		Date dateLimit= new Date()
		dateLimit = dateLimit.plus(7)
		return !startDate.after(dateLimit) && !endDate.before(today)
		
	}
	
	def isMonthly = {
		//es para esta semana si la fecha de inicio es
		Date today = new Date()
		Date dateLimit= new Date()
		dateLimit = dateLimit.plus(30)
		return !startDate.after(dateLimit) && !endDate.before(today)
	}
}