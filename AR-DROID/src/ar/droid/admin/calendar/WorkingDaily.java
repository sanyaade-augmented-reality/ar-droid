package ar.droid.admin.calendar;

public class WorkingDaily extends EventCalendar {

	public String toString() {
		return "WorkingDaily";
	}

	@Override
	public String getLinea1() {
		String linea = "De " + this.formatNumber(this.getStartDate().getHours()) + ":"
				+ this.formatNumber(this.getStartDate().getMinutes()) + " a "
				+ this.formatNumber(this.getEndDate().getHours()) + ":"
				+ this.formatNumber(this.getEndDate().getMinutes());
		return linea;
	}

	@Override
	public String getLinea2() {
		return "De Lunes a Viernes";
	}
}
