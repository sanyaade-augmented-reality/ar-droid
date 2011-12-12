package ar.droid.admin.calendar;


public class Weekly extends RepeatCalendar {
	private String[] dias = {"Domingo", "Lunes","Martes","Mi�rcoles","Jueves","Virnes","S�bado"};
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
		return "Los d�as " + this.getDay();
	}

	private String getDay() {
		return dias[this.getDayOfWeek()];
	}
}
