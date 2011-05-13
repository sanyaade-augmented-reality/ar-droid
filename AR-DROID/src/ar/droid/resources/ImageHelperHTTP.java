package ar.droid.resources;

import android.graphics.drawable.Drawable;

public class ImageHelperHTTP extends ImageHelper {

	public Drawable getImage(String Url) {
		return  loadImage(Url);
	}

}
