package ar.droid.view;

import ar.droid.model.Entity;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class EntityOverlayItem extends OverlayItem {
	
	private Entity entity;
	
	public EntityOverlayItem(GeoPoint geoPoint,Entity entity) {
		this(geoPoint,entity.getName(),entity.getDescription(),entity);
	}

	public EntityOverlayItem(GeoPoint geoPoint,String title, String snipet,Entity entity) {
		super(geoPoint,title,snipet);
		setEntity(entity);
	}
	
	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}
