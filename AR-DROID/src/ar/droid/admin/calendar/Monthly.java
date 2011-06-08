package ar.droid.admin.calendar;

public class Monthly extends RepeatCalendar {

	private Integer dayOfMonth;
	
	public Integer getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public String toString() {
		return "Monthly";
	}
}
