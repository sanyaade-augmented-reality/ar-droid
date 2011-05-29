package ar.droid.resources;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;
import ar.droid.config.ARDROIDProperties;
import ar.droid.connection.ConnectionUtil;


public abstract class ImageHelper implements IImageHelper {
	private static Map<String, Drawable> _IMAGES = null;
	
	public ImageHelper(){
		if(_IMAGES == null)
			_IMAGES = new HashMap<String, Drawable>();
	}
	protected Drawable loadImage(String url) {
		if(_IMAGES.containsKey(url))
			return _IMAGES.get(url);
					
		String urlServer = ARDROIDProperties.getInstance().getProperty("ar.droid.server");
		InputStream instream = ConnectionUtil.getInputStream(urlServer+url);
		if (instream != null){
			Drawable drawable = Drawable.createFromStream(instream, "offers task");
			_IMAGES.put(url, drawable);
			return drawable;
		}
		return null;
    }
}