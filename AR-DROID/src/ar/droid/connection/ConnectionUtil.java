package ar.droid.connection;

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

public class ConnectionUtil {
	
	public static InputStream getInputStream(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget;
		try {
			httpget = new HttpGet(new URI(url.toString()));
			HttpResponse response;
			response = httpclient.execute(httpget);
		    HttpEntity entity = response.getEntity();
		     
		    if (entity != null) {
		        InputStream instream = entity.getContent();
		        
		        return instream;
		    }
		        
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
