package ar.droid.admin.calendar;

import java.util.Date;


public abstract class EventCalendar {
	
	private Date startDate;
	private Date endDate;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String description(){
		return "";
	}
	
	public abstract String getLinea1();
	
	public abstract String getLinea2();
	
	protected String formatNumber(Integer number){
		if(number > 9)
			return number.toString();
		return "0" + number.toString();
	}
}
