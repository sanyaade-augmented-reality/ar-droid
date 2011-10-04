package ar.droid.model.deserializer;

import java.lang.reflect.Type;

import ar.droid.model.Event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EventSerializer implements JsonSerializer<Event> {

	@Override
	public JsonElement serialize(Event event, Type arg1,
			JsonSerializationContext arg2) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		JsonObject jsonObject = (JsonObject) gson.toJsonTree(event);
		jsonObject.addProperty("class", event.getClass().getName());
		return jsonObject;
	}

}
