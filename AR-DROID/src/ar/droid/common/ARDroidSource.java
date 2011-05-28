package ar.droid.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import android.graphics.Color;
import ar.droid.ar.common.DataSource;
import ar.droid.ar.view.Marker;
import ar.droid.config.ARDROIDProperties;
import ar.droid.config.Request;
import ar.droid.model.Entity;
import ar.droid.model.ResourceHelperFactory;

public class ARDroidSource extends DataSource {

	@Override
	public String createRequestURL(double lat, double lon, double alt, float radius, String locale) {
		return ARDROIDProperties.getInstance().getProperty("ar.droid.server") + Request.GET_ENTITIES;
	}

	@Override
	public List<Marker> parse(JSONObject root) {
		List<Marker> lsResult = new ArrayList<Marker>();
		//Se recupera las entidades a mostrar en pantalla
		List<Entity> lsEntity = ResourceHelperFactory.createResourceHelper().getEntities();
		for (Iterator<Entity> iterator = lsEntity.iterator(); iterator.hasNext();) {
			Entity entity = iterator.next();
			lsResult.add(this.convertToMarker(entity));
		}
		return lsResult;
	}

	private Marker convertToMarker(Entity entity) {
		Marker marker = new Marker(entity.getName(), entity.getGeoPoint().getLatitudeE6(), entity.getGeoPoint().getLongitudeE6(), entity.getGeoPoint().getAltitude(), Color.BLACK);
		return marker;
	}

}
