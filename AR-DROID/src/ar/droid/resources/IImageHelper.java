package ar.droid.resources;

import android.graphics.drawable.Drawable;
import ar.droid.model.Entity;
import ar.droid.model.Event;
import ar.droid.model.TypeEntity;

public interface IImageHelper {
	public Drawable getImage(String Url);
	public Drawable getImageEvent(Event event);
	public Drawable getImageEntity(Entity entity);
	public Drawable getIconTypeEntity(TypeEntity typeEntity);
	
}
