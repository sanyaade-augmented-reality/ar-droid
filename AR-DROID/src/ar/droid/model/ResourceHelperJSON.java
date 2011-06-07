package ar.droid.model;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ar.droid.admin.reader.ReaderNews;
import ar.droid.config.ARDROIDProperties;
import ar.droid.config.Request;
import ar.droid.location.GeoPoint;
import ar.droid.model.deserializer.GeoPointDeserializer;
import ar.droid.model.deserializer.ReaderNewsDeserializer;


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
		Gson gson =	gsonBuilder.create();	
		
		List<Event> xLsResult  = gson.fromJson(inputStream,listType);
	
		return xLsResult;
	}
}
