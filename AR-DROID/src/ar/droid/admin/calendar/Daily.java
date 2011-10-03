package ar.droid.admin.calendar;

public class Daily extends EventCalendar {
	
	public String toString() {
		return "Daily";
	}

	@Override
	public String getLinea1() {
		String linea = "De " + this.getStartDate().getHours() + ":" + this.getStartDate().getMinutes()
				+ " a " + this.getEndDate().getHours() + ":" + this.getEndDate().getMinutes();
		return linea;
	}

	@Override
	public String getLinea2() {
		return "Todos los días";
	}
}
