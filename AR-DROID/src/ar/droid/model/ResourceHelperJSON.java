package ar.droid.model;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.droid.admin.calendar.EventCalendar;
import ar.droid.admin.reader.ReaderNews;
import ar.droid.admin.survey.SurveyTemplate;
import ar.droid.admin.survey.response.Response;
import ar.droid.admin.survey.response.Summary;
import ar.droid.admin.survey.response.SurveyResponse;
import ar.droid.config.AppPreferences;
import ar.droid.config.Request;
import ar.droid.connection.RESTClient;
import ar.droid.location.GeoPoint;
import ar.droid.model.deserializer.EventCalendarDeserializer;
import ar.droid.model.deserializer.EventSerializer;
import ar.droid.model.deserializer.GeoPointDeserializer;
import ar.droid.model.deserializer.ReaderNewsDeserializer;
import ar.droid.model.deserializer.ResponseSerializer;
import ar.droid.model.deserializer.SurveyTemplateDeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class ResourceHelperJSON extends ResourceHelper {
	private String urlServer = AppPreferences.getString("urlServerPref", "http://www.gabrielnegri.com.ar:8080/server");
	
	public List<Entity> getEntities(){
		
		Reader inputStream = loadManySerialized(urlServer + Request.GET_ENTITIES);
		Type listType = new TypeToken<ArrayList<Entity>>() {}.getType();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(GeoPoint.class,new GeoPointDeserializer());
		gsonBuilder.registerTypeAdapter(ReaderNews.class,new ReaderNewsDeserializer());
		Gson gson =	gsonBuilder.create();	
		
		List<Entity> xLsResult  = gson.fromJson(inputStream,listType);
		return xLsResult;
	}
	
	@Override
	public List<Event> getEvents(Entity entity) {
		Reader inputStream = loadManySerialized(urlServer + Request.GET_EVENTS + "/"+entity.getId());
		Type listType = new TypeToken<ArrayList<Event>>() {}.getType();
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(GeoPoint.class,new GeoPointDeserializer());
		gsonBuilder.registerTypeAdapter(EventCalendar.class,new EventCalendarDeserializer());
		gsonBuilder.registerTypeAdapter(SurveyTemplate.class,new SurveyTemplateDeserializer());
		Gson gson =	gsonBuilder.create();	
		
		List<Event> events  = gson.fromJson(inputStream,listType);
		
		Iterator<Event> it = events.iterator();
		
		while (it.hasNext()) {
			Event event = (Event) it.next();
			event.setEntity(entity);			
		}
		return events;
	}
	
	@Override
	public List<Activity> getActivities(Long idEvent) {
		Reader inputStream = loadManySerialized(urlServer + Request.GET_ACTIVITIES + "/" + idEvent);
		Type listType = new TypeToken<ArrayList<Activity>>() {}.getType();
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(TypeActivity.class,new TypeActivity());
		Gson gson =	gsonBuilder.create();	
		
		List<Activity> activities = gson.fromJson(inputStream,listType);
		return activities;
	}
	
	public void saveResponse(SurveyResponse surveyResponse){			
		GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.registerTypeAdapter(Response.class, new ResponseSerializer());
		Gson gson =	gsonBuilder.create();
		RESTClient.doPut(gson.toJsonTree(surveyResponse).getAsJsonObject(), urlServer + Request.PUT_FEEDBACK);
	}
	
	public void makeVisit(Event event){			
		GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.registerTypeAdapter(Event.class, new EventSerializer());
		Gson gson =	gsonBuilder.create();
		RESTClient.doPut(gson.toJsonTree(event).getAsJsonObject(), urlServer + Request.PUT_CLIENT_VISIT);
	}

	@Override
	public List<Event> getEvents() {
		return getEvents("");
	}
	
	@Override
	public List<Event> getEvents(String option) {
		String url = urlServer + Request.GET_ALL_EVENTS;
		if (!"".equals(option))
			url = url + "/" + option;		
		Reader inputStream = loadManySerialized(url);
		return getEvents(inputStream);
	}

	@Override
	public List<TypeEvent> getTypeEvents() {
		String url = urlServer + Request.GET_ALL_TYPE_EVENTS;
		Reader inputStream = loadManySerialized(url);
		Type listType = new TypeToken<ArrayList<TypeEvent>>() {}.getType();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson =	gsonBuilder.create();	
		List<TypeEvent> events  = gson.fromJson(inputStream,listType);
		return events;
	}

	@Override
	public List<TypeEntity> getTypeEntities() {
		String url = urlServer + Request.GET_ALL_TYPE_ENTITIES;
		Reader inputStream = loadManySerialized(url);
		Type listType = new TypeToken<ArrayList<TypeEntity>>() {}.getType();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson =	gsonBuilder.create();	
		List<TypeEntity> typeEntities  = gson.fromJson(inputStream,listType);
		return typeEntities;
	}

	@Override
	public List<TypeActivity> getTypeActivities() {
		String url = urlServer + Request.GET_ALL_TYPE_ACTIVITIES;
		Reader inputStream = loadManySerialized(url);
		Type listType = new TypeToken<ArrayList<TypeEntity>>() {}.getType();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson =	gsonBuilder.create();	
		List<TypeActivity> typeActivities  = gson.fromJson(inputStream,listType);
		return typeActivities;
	}

	@Override
	public Summary getSummary(Event event) {
		String url = urlServer + Request.GET_SUMMARY_FEEDBACK + "/"+event.getId();
		Reader inputStream = loadManySerialized(url);
		return (new Gson()).fromJson(inputStream, Summary.class);
			
	}

	@Override
	public List<Event> searchEvents(String text) {
		String url = urlServer + Request.GET_SEARCH_EVENTS + "?text="+text;
		Reader inputStream = loadManySerialized(url);
		if (inputStream!=  null)
			return getEvents(inputStream);
		else return new ArrayList<Event>();
	}
	
	
	private List<Event> getEvents(Reader inputStream) {
		Type listType = new TypeToken<ArrayList<Event>>() {}.getType();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(GeoPoint.class,new GeoPointDeserializer());
		gsonBuilder.registerTypeAdapter(EventCalendar.class,new EventCalendarDeserializer());
		gsonBuilder.registerTypeAdapter(SurveyTemplate.class,new SurveyTemplateDeserializer());
		Gson gson =	gsonBuilder.create();			
		List<Event> events  = gson.fromJson(inputStream,listType);		
		Iterator<Event> it = events.iterator();		
		while (it.hasNext()) {
			Event event = (Event) it.next();
			event.setEntity(Resource.getInstance().getEntity(event.getIdEntity()));
			event.getEntity().addEvent(event);
		}	
		return events;
	}
	
	
}
