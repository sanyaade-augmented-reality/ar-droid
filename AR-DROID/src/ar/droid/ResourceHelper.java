package ar.droid;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

public class ResourceHelper {
	
	public List<Entity> getEntities(){
		Reader inputStream = loadManySerialized("http://190.192.253.92:8080/AR-DROID-ADMIN/entity/entityJson");
		//Lo("MY INFO", inputStream.toString());
		Gson gson = new Gson();
		Entities xEntidades = gson.fromJson(inputStream, Entities.class);
		return xEntidades.getEntities();
	}
	
	public Reader loadManySerialized(String url){
			HttpClient httpclient = new DefaultHttpClient();
	        try {
	        	HttpGet httpget;
	 			
	 			httpget = new HttpGet(new URI(url));
	 			

	 	        // Execute the request
	 	        HttpResponse response;

	 	        String result = null;
	            response = httpclient.execute(httpget);

	            // Get hold of the response entity
	            HttpEntity entity = response.getEntity();
	            // If the response does not enclose an entity, there is no need
	            // to worry about connection release

	            if (entity != null) {
	                // A Simple Response Read
	                InputStream instream = entity.getContent();
	                return new InputStreamReader(instream);
	                //result = convertStreamToString(instream);

	                // Closing the input stream will trigger connection release
	                //instream.close();
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
	       

	        return null;
	    }

	 /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
	/*private static String convertStreamToString(InputStream is) {
       
        BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8192);
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }/*/

}
