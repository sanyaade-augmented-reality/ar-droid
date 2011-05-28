package ar.droid.admin.reader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class Message implements Comparable<Message>{

	static SimpleDateFormat FORMATTER =  new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",Locale.ENGLISH);
	
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
		//return updated;
		return FORMATTER.format(date);
	}


	public void setDate(String date) {
		//this.updated = updated;
		 while (!date.endsWith("00")){
	           date += "0";
	     }
	     try {
	          this.date = FORMATTER.parse(date.trim());
	     } catch (java.text.ParseException e) {
	    	 Log.e("error","",e);
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
