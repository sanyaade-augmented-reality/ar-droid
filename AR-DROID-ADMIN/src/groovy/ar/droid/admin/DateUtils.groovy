package ar.droid.admin

import java.util.Date
import java.text.ParseException
import java.text.SimpleDateFormat

class DateUtils {
	
	public static Date getDate(String day, String month, String year, String hour, String minute) throws ParseException{
		if(month.length() == 1)
			month = "0" + month
		SimpleDateFormat xFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm")
		Date date = xFormatter.parse(day + "/" + month + "/" + year + " " + hour + ":" + minute)
		
		System.out.println("string = " + day + "/" + month + "/" + year + " " + hour + ":" + minute)
		System.out.println("date = " + date)
		
		return date
	}
}