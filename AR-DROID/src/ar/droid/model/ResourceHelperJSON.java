package ar.droid.model;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ar.droid.admin.calendar.EventCalendar;
import ar.droid.admin.reader.ReaderNews;
import ar.droid.admin.survey.SurveyTemplate;
import ar.droid.admin.survey.response.Response;
import ar.droid.admin.survey.response.SurveyResponse;
import ar.droid.config.ARDROIDProperties;
import ar.droid.config.Request;
import ar.droid.connection.RESTClient;
import ar.droid.location.GeoPoint;
import ar.droid.model.deserializer.EventCalendarDeserializer;
import ar.droid.model.deserializer.GeoPointDeserializer;
import ar.droid.model.deserializer.ReaderNewsDeserializer;
import ar.droid.model.deserializer.ResponseSerializer;
import ar.droid.model.deserializer.SurveyTemplateDeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class ResourceHelperJSON extends ResourceHelper {

	public List<Entity> getEntities(){
		String urlServer = ARDROIDProperties.getInstance().getProperty("ar.droid.server");
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
		String urlServer = ARDROIDProperties.getInstance().getProperty("ar.droid.server");
		Reader inputStream = loadManySerialized(urlServer + Request.GET_EVENTS + "/"+entity.getId());
		Type listType = new TypeToken<ArrayList<Event>>() {}.getType();
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(GeoPoint.class,new GeoPointDeserializer());
		gsonBuilder.registerTypeAdapter(EventCalendar.class,new EventCalendarDeserializer());
		gsonBuilder.registerTypeAdapter(SurveyTemplate.class,new SurveyTemplateDeserializer());
		Gson gson =	gsonBuilder.create();	
		
		List<Event> xLsResult  = gson.fromJson(inputStream,listType);
	
		return xLsResult;
	}
	
	public void saveResponse(SurveyResponse surveyResponse){
		String urlServer = ARDROIDProperties.getInstance().getProperty("ar.droid.server");				
		GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.registerTypeAdapter(Response.class, new ResponseSerializer());
		Gson gson =	gsonBuilder.create();
		RESTClient.doPut(gson.toJsonTree(surveyResponse).getAsJsonObject(), urlServer + "/request/feedback");
		
	}
}
