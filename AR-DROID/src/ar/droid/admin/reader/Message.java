package ar.droid.admin.reader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.util.Log;

public class Message implements Comparable<Message>{

	static SimpleDateFormat FORMATTER =  new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",Locale.ENGLISH);
	static SimpleDateFormat FORMATTER_2 =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.ENGLISH);
	
	private String title;
	private String link;
	private Date date;
	private String description;
	
	
	public int compareTo(Message another) {
        if (another == null) return 1;
        return another.getDate().compareTo(getDate());
    }

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getDate() {
		GregorianCalendar hoy = new GregorianCalendar();
		//GregorianCalendar calHasta = new GregorianCalendar();
		//Calendar hoy = Calendar.getInstance();
		
		if (hoy.getTime().compareTo(date)==0)
			return "Hoy";
		else{
			hoy.add(Calendar.DATE, -1);
			if (hoy.getTime().compareTo(date)==0)
				return "Ayer";
		}
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es_ES"));
		return formateador.format(date);		
		
		
	}


	public void setDate(String date) {
		 String tempDate = date;
		 while (!tempDate.endsWith("00")){
			 tempDate += "0";
	     }
		 
	     try {
	          this.date = FORMATTER.parse(tempDate.trim());
	     } catch (java.text.ParseException e) {
	    	 try{
	    		 this.date = FORMATTER_2.parse(date.trim());
	    	 }
	    	 catch (java.text.ParseException e2) {
		    	 Log.e("error","",e2);
	    	 }
	     }
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Titulo" + getTitle();
	}


	
	
	
}
