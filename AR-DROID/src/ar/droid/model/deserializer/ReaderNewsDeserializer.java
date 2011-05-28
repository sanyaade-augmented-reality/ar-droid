package ar.droid.model.deserializer;

import java.lang.reflect.Type;

import android.util.Log;
import ar.droid.admin.reader.ReaderNews;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ReaderNewsDeserializer implements JsonDeserializer<ReaderNews> {

	@Override
	public ReaderNews deserialize(JsonElement json, Type type,JsonDeserializationContext context) throws JsonParseException {
		Log.i("class **** ",json.getAsJsonObject().get("class").getAsString());
		String nameClass = json.getAsJsonObject().get("class").getAsString();
		try {
			Class c = Class.forName(nameClass);
			ReaderNews object = (ReaderNews)c.newInstance();
			object.setParameter( json.getAsJsonObject().get("parameter").getAsString());
			return object;
		} catch (ClassNotFoundException e) {
			Log.e("ReaderNewsDeserializer", "No se encontro la clase", e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		//RSS, FAcebook o TWeet
		return null;
	}

}
