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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import android.util.Log;

import com.google.gson.JsonObject;

public class RESTClient {
	
	
	public static void doPut(final JsonObject data,final String url) {
		Log.i("doPut","url " + url + " -JSON " + data.toString());
		final HttpClient httpclient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000);		
		Thread thread = new Thread(){
        	public void run() {
        		try {
        			HttpPut httpPut = new HttpPut(url);
        			httpPut.addHeader("Accept", "application/json");
        			httpPut.addHeader("Content-Type", "application/json");
        			StringEntity entity = new StringEntity(data.toString(), "UTF-8");
        			entity.setContentType("application/json");
        			httpPut.setEntity(entity);
        	        HttpResponse response = httpclient.execute(httpPut);
        	        
        		} catch (Exception e) {
        			Log.e("Error","doPut",e);
        			e.printStackTrace();
        			
        		}              
        	}
        };  
        
        thread.start();
		
	}
	
	public static InputStream doGet(String url) {
		Log.i("doGet",url);
		HttpClient httpclient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000);
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
			e.printStackTrace();
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
        return null;
    }
}
