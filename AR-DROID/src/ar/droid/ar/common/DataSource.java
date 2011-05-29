package ar.droid.ar.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import ar.droid.ar.view.IconMarker;
import ar.droid.ar.view.Marker;
import ar.droid.model.Entity;
import ar.droid.model.Resource;
import ar.droid.resources.ImageHelperHTTP;

public class DataSource {
	private static Logger logger = Logger.getLogger(DataSource.class.getSimpleName());

	protected static final int MAX = 10;

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
		Drawable draw = new ImageHelperHTTP().getImage(entity.getTypeEntity().getIconUrl());
		Bitmap bitmap = ((BitmapDrawable) draw).getBitmap();

		IconMarker marker = new IconMarker(entity.getName(), entity.getGeoPoint().getLatitudeE6(),
				entity.getGeoPoint().getLongitudeE6(), entity.getGeoPoint().getAltitude(),
				Color.BLACK, bitmap);
		return marker;
	}

}
