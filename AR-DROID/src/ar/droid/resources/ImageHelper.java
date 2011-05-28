package ar.droid.resources;

import java.io.InputStream;

import android.graphics.drawable.Drawable;
import ar.droid.config.ARDROIDProperties;
import ar.droid.connection.ConnectionUtil;


public abstract class ImageHelper implements IImageHelper {
	
	protected Drawable loadImage(String url){
			String urlServer = ARDROIDProperties.getInstance().getProperty("ar.droid.server");
		   InputStream instream = ConnectionUtil.getInputStream(urlServer+url);
		   if (instream != null){
			   Drawable drawable = Drawable.createFromStream(instream, "offers task");
			   return drawable;
		   }
		   
		   return null;
		   
           		       

        
    }
}
