package ar.droid.admin.calendar;


public class Weekly extends RepeatCalendar {
	private String[] dias = {"Domingo", "Lunes","Martes","Miércoles","Jueves","Virnes","Sábado"};
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
		return "Los días " + this.getDay();
	}

	private String getDay() {
		return dias[this.getDayOfWeek()];
	}
}
