package ar.droid.driving;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import ar.droid.location.GeoPoint;
import com.google.gson.Gson;

public class DrivingDirectionsGoogleJSON extends DrivingDirections {
	
	
	protected RoutePath startDrivingTo (GeoPoint startPoint, GeoPoint endPoint, Mode mode)	{
		Log.i("startDrivingTo ","startDrivingTo");
		Reader inputStream = doConnectToService(startPoint, endPoint, mode);
		//parsean el objeto json con libreria Gson
		Gson gson = new Gson();
		RoutePath routePath = gson.fromJson(inputStream, RoutePath.class);
		Log.i("route",""+routePath.getRoutes().size());
		//aca convertir los objetos polyline
		doConvertPolyline(routePath);
		return routePath;
		
	}
	
	private void doConvertPolyline(RoutePath routePath){
		if (routePath.getRoute() != null){
			Iterator<Leg> xIterator = routePath.getRoute().getLegs().iterator();
			while (xIterator.hasNext()) {
				Leg leg = xIterator.next();
				Iterator<Step> xItSteps = leg.getSteps().iterator();
				while (xItSteps.hasNext()) {
					Step step = xItSteps.next();
					doConvertPolyline(step.getPolyline());				
				}
			}
		}		
	}
	
	private void doConvertPolyline(Polyline polyline){
		//List<GeoPoint> poly = new ArrayList<GeoPoint>(); 
		int index = 0, len = polyline.getPoints().length();  
		int lat = 0, lng = 0;  		
		while (index < len) {  
		     int b, shift = 0, result = 0;  
		     do {  
		          b = polyline.getPoints().charAt(index++) - 63;  
		           result |= (b & 0x1f) << shift;  
		           shift += 5;  
		     } while (b >= 0x20);  
		     
		     int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));  
		     lat += dlat;  
		   
		     shift = 0;  
		     result = 0;  
		     do {  
		    	 b = polyline.getPoints().charAt(index++) - 63;  
		         result |= (b & 0x1f) << shift;  
		         shift += 5;  
			} while (b >= 0x20);  
			
		     int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));  
			 lng += dlng;  
			 
			 GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6), (int) (((double) lng / 1E5) * 1E6));  
			 polyline.addToPolyline(p);  
		 }  
	}
	
	private Reader doConnectToService(GeoPoint startPoint, GeoPoint endPoint, Mode mode) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.google.com/maps/api/directions/json?");
		urlString.append("&origin=");//from
		urlString.append( Double.toString((double)startPoint.getLatitudeE6()/1.0E6 ));
		urlString.append(",");
		urlString.append( Double.toString((double)startPoint.getLongitudeE6()/1.0E6 ));
		urlString.append("&destination=");//to
		urlString.append( Double.toString((double)endPoint.getLatitudeE6()/1.0E6 ));
		urlString.append(",");
		urlString.append( Double.toString((double)endPoint.getLongitudeE6()/1.0E6 ));
		urlString.append("&language=es");//ver para interna
		urlString.append("&sensor=true");
		urlString.append("&mode="+mode);
		//urlString.append("&ie=UTF8&0&om=0");
		HttpClient httpclient = new DefaultHttpClient();
        try {
        	HttpGet httpget;
 			httpget = new HttpGet(new URI(urlString.toString()));
 			
 	        HttpResponse response;

 	        Log.i(" conect to entity ",urlString.toString());
 	        response = httpclient.execute(httpget);
 	        
 	        HttpEntity entity = response.getEntity();
 	        InputStream instream = entity.getContent();
 	       
 	        Log.i(" doConnectToService entity ",entity.toString());
 	        Log.i(" doConnectToService instream ",instream.toString());
            
 	        return new InputStreamReader(instream);
           
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


	
}
