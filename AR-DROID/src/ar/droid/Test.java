package ar.droid;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Test extends MapActivity {
    /** Called when the activity is first created. */
	private MapView mapView;
	private Resources resources;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        resources = getResources();
    	//mapView.getController().setZoom(17);
        
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, new LocationListenerGPS());
    }
    
   
    
    private class LocationListenerGPS implements android.location.LocationListener{
		@Override
		public void onLocationChanged(Location location) {
			GeoPoint position = new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude()*1E6));
			
			
	        List<Overlay> mapOverlays = mapView.getOverlays();
			Drawable drawable = resources.getDrawable(R.drawable.androidmarker);
			//drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

			TestOverlay itemizedoverlay = new TestOverlay(drawable);     
	 		OverlayItem overlayitem = new OverlayItem(position, "Hola, Mundo!", "Estoy Aqui!");
			
			itemizedoverlay.addOverlay(overlayitem);
			mapOverlays.add(itemizedoverlay);
			
			// move to location
			mapView.getController().animateTo(position);
	 
			// redraw map
			mapView.postInvalidate();
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    	
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    
    
}