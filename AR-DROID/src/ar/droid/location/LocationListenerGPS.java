package ar.droid.location;

import com.google.android.maps.MapView;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class LocationListenerGPS implements LocationListener {
	static String TAG = "LocationListenerGPS";
	private MapView mapView;
	
	public LocationListenerGPS(MapView map){
		mapView = map;
	}
	
	@Override
	public void onLocationChanged(Location location) {
    	Log.d(TAG, "Posición recibida de GPS");
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
