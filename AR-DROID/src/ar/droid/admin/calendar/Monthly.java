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

	@Override
	public String getLinea1() {
		String linea = "De " + this.getStartDate().getHours() + ":"
				+ this.getStartDate().getMinutes() + " a "
				+ this.getEndDate().getHours() + ":"
				+ this.getEndDate().getMinutes();
		return linea;
	}

	@Override
	public String getLinea2() {
		return "El día " + this.getDayOfMonth() + " de cada mes";
	}
}
