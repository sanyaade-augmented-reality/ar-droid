package ar.droid.model.deserializer;

import java.lang.reflect.Type;

import android.util.Log;

import ar.droid.location.GeoPoint;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GeoPointDeserializer implements JsonDeserializer<GeoPoint> {

	@Override
	public GeoPoint deserialize(JsonElement json, Type type, JsonDeserializationContext context)throws JsonParseException {
		Log.i("latitude", json.getAsJsonObject().get("latitude").getAsString());
		Log.i("longitude", json.getAsJsonObject().get("longitude").getAsString());

		double lat = json.getAsJsonObject().get("latitude").getAsDouble();
		double lng = json.getAsJsonObject().get("longitude").getAsDouble();
		double alt =0;
		if (!json.getAsJsonObject().get("altitude").isJsonNull())
			alt = json.getAsJsonObject().get("altitude").getAsDouble();
		GeoPoint p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6), alt);
		return p; 
	}

}
