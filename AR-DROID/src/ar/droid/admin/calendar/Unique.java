package ar.droid.admin.calendar;

public class Unique extends EventCalendar {

	
	public String toString() {
		return "Unique";
	}

	@Override
	public String getLinea1() {
		String linea = "El día " + this.getStartDate().getDate() + "/" + (this.getStartDate().getMonth() + 1) + "/"
				+ (this.getStartDate().getYear() + 1900);
		return linea;
	}

	@Override
	public String getLinea2() {
		String linea = "De " + this.formatNumber(this.getStartDate().getHours()) + ":"
				+ this.formatNumber(this.getStartDate().getMinutes()) + " a "
				+ this.formatNumber(this.getEndDate().getHours()) + ":"
				+ this.formatNumber(this.getEndDate().getMinutes());
		return linea;
	}
	
}
