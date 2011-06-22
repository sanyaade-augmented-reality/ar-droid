package ar.droid.model.deserializer;

import java.lang.reflect.Type;

import ar.droid.admin.survey.response.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ResponseSerializer implements JsonSerializer<Response> {

	@Override
	public JsonElement serialize(Response response, Type arg1,JsonSerializationContext arg2) {
		Gson gson =	new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		JsonObject jsonObject = (JsonObject)gson.toJsonTree(response);
		jsonObject.addProperty("class",response.getClass().getName());
		return jsonObject;		
	}

}
