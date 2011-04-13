package ar.droid.util

import java.text.SimpleDateFormat;

class UtilDate {
	
	String TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
	String TIME_ZONE_EVENT_FACEBOOK = "America/Los_Angeles";
	
	def formatDateEventFacebook(longDate) {
		SimpleDateFormat objSdf = new SimpleDateFormat(TIME_FORMAT);
		objSdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_EVENT_FACEBOOK));
		
		Calendar cal = Calendar.getInstance()
		cal.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_EVENT_FACEBOOK))
		cal.setTimeInMillis(new Long(longDate) * 1000)
		
		def date = objSdf.format(cal.getTime());
		
		return new SimpleDateFormat(TIME_FORMAT).parse(date);
		
	}
	
}
