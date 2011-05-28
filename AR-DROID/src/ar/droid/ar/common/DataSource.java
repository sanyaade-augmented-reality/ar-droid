package ar.droid.ar.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import android.graphics.Color;
import ar.droid.ar.view.Marker;
import ar.droid.model.Entity;
import ar.droid.model.Resource;

public class DataSource {
	private static Logger logger = Logger.getLogger(DataSource.class.getSimpleName());

	protected static final int MAX = 5;

	public List<Marker> getEntities() {
		List<Marker> lsResult = new ArrayList<Marker>();
		// Se recupera las entidades a mostrar en pantalla
		List<Entity> lsEntity = Resource.getInstance().getEntities();
		for (Iterator<Entity> iterator = lsEntity.iterator(); iterator.hasNext();) {
			Entity entity = iterator.next();
			lsResult.add(this.convertToMarker(entity));
		}
		return lsResult;
	}

	private Marker convertToMarker(Entity entity) {
		Marker marker = new Marker(entity.getName(), entity.getGeoPoint().getLatitudeE6(), entity
				.getGeoPoint().getLongitudeE6(), entity.getGeoPoint().getAltitude(), Color.BLACK);
		return marker;
	}

}
