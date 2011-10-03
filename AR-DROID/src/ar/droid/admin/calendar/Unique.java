package ar.droid.admin.calendar;

public class Unique extends EventCalendar {

	
	public String toString() {
		return "Unique";
	}

	@Override
	public String getLinea1() {
		String linea = "El d�a " + this.getStartDate().getDate() + "/" + this.getStartDate().getMonth() + "/"
				+ this.getStartDate().getYear();
		return linea;
	}

	@Override
	public String getLinea2() {
		String linea = "De " + this.getStartDate().getHours() + ":"
				+ this.getStartDate().getMinutes() + " a "
				+ this.getEndDate().getHours() + ":"
				+ this.getEndDate().getMinutes();
		return linea;
	}
	
}
