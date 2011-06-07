package ar.droid.resources;

import android.graphics.drawable.Drawable;
import ar.droid.config.Request;
import ar.droid.model.Entity;
import ar.droid.model.Event;
import ar.droid.model.TypeEntity;

public class ImageHelperHTTP extends ImageHelper {

	public Drawable getImage(String Url) {
		return  loadImage(Url);
	}

	@Override
	public Drawable getImageEvent(Event event) {
		return loadImage(Request.GET_IMAGE_EVENT + "/" + event.getId());
	}

	@Override
	public Drawable getImageEntity(Entity entity) {
		return loadImage(Request.GET_IMAGE_ENTITY + "/" + entity.getId());
	}

	@Override
	public Drawable getIconTypeEntity(TypeEntity typeEntity) {
		return loadImage(Request.GET_ICON_TYPE_ENTITY + "/" + typeEntity.getId());
	}

}
