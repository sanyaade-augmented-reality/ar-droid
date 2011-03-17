package ar.droid.admin

class Monthly extends RepeatCalendar {

	Integer dayOfMonth;
	
    static constraints = {
		dayOfMonth(range:1..31);
    }
}
