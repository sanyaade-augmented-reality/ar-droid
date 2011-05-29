package ar.droid.resources;

import java.io.InputStream;
import java.util.Map;

import android.graphics.drawable.Drawable;
import ar.droid.config.ARDROIDProperties;
import ar.droid.connection.ConnectionUtil;


public abstract class ImageHelper implements IImageHelper {
	private Map<String, Drawable> images;
	
	protected Drawable loadImage(String url) {
		if(this.images.containsKey(url))
			return this.images.get(url);
					
		String urlServer = ARDROIDProperties.getInstance().getProperty("ar.droid.server");
		InputStream instream = ConnectionUtil.getInputStream(urlServer+url);
		if (instream != null){
			Drawable drawable = Drawable.createFromStream(instream, "offers task");
			this.images.put(url, drawable);
			return drawable;
		}
		return null;
    }
}