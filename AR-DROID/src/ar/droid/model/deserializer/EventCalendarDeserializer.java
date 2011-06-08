package ar.droid.model.deserializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.util.Log;
import ar.droid.admin.calendar.EventCalendar;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class EventCalendarDeserializer implements JsonDeserializer<EventCalendar> {

	@Override
	public EventCalendar deserialize(JsonElement json, Type type,JsonDeserializationContext context) throws JsonParseException {
		//Log.i("class **** ",json.getAsJsonObject().get("class").getAsString());
		//despues utilizar para el tipo correcto
		String nameClass = json.getAsJsonObject().get("class").getAsString();
		
		EventCalendar eventCalendar = new EventCalendar();
		
		try {
			Class c = Class.forName(nameClass);
			eventCalendar = (EventCalendar)c.newInstance();
			
			//falta setar algunos campos para monthy y weekly

		} catch (ClassNotFoundException e) {
			Log.e("EventCalendarDeserializer", "No se encontro la clase", e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.ENGLISH);
		
		String initDate= json.getAsJsonObject().get("startDate").getAsString();
		String endDate= json.getAsJsonObject().get("endDate").getAsString();
		
		
		try {
			eventCalendar.setStartDate(simpleDateFormat.parse(initDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			eventCalendar.setEndDate(simpleDateFormat.parse(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return eventCalendar;
	}
}
