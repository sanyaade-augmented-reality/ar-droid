package ar.droid.admin.calendar;

public class Weekly extends RepeatCalendar {

	private Integer dayOfWeek;

	public Integer getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public String toString() {
		return "Weekly";
	}
}
