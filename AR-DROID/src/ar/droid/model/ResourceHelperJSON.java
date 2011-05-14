package ar.droid.model;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ar.droid.config.ARDROIDProperties;
import ar.droid.config.Request;
import ar.droid.model.deserializer.GeoPointDeserializer;

import com.google.android.maps.GeoPoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class ResourceHelperJSON extends ResourceHelper {

	public List<Entity> getEntities(){
		String urlServer = ARDROIDProperties.getInstance().getProperty("ar.droid.server");
		Reader inputStream = loadManySerialized(urlServer + Request.GET_ENTITIES);
		Type listType = new TypeToken<ArrayList<Entity>>() {}.getType();
		//Lo("MY INFO", inputStream.toString());
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(GeoPoint.class,new GeoPointDeserializer());
		Gson gson =	gsonBuilder.create();	
		
		List<Entity> xLsResult  = gson.fromJson(inputStream,listType);
		return xLsResult;
	}
}
