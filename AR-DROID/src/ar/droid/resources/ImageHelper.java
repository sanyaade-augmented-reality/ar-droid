package ar.droid.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.drawable.Drawable;
import android.util.Log;
import ar.droid.config.ARDROIDProperties;


public abstract class ImageHelper implements IImageHelper {
	
	protected Drawable loadImage(String url){
		HttpClient httpclient = new DefaultHttpClient();
		StringBuilder urlString = new StringBuilder();
		String urlServer = ARDROIDProperties.getInstance().getProperty("ar.droid.server");
		urlString.append(urlServer);
		urlString.append(url);
		Log.i("URLIMAGEN", urlString.toString());
        try {
        	HttpGet httpget;
 			httpget = new HttpGet(new URI(urlString.toString()));
 		
 	        // Execute the request
 	        HttpResponse response;

 	        //String result = null;
            response = httpclient.execute(httpget);

            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release

            if (entity != null) {
                // A Simple Response Read
                InputStream instream = entity.getContent();
                //BufferedInputStream bis = new BufferedInputStream(instream);
             
                /* Decode url-data to a bitmap. */
               // Bitmap bm = BitmapFactory.decodeStream(instream);
                Drawable drawable = Drawable.createFromStream(instream, "offers task");
              
                
               // instream.close();
               // bis.close();
                
                return drawable;
            }
        
        } 
        catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e) {
        	Log.e("45t5",e.getMessage());
		}
       

        return null;
    }
}
