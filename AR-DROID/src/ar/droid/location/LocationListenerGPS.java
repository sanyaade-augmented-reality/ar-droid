package ar.droid.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocationListenerGPS implements LocationListener {

	private Location curLocation;
    private boolean locationChanged = false; 

	@Override
	public void onLocationChanged(Location location) {
		if(curLocation == null)   {
           curLocation = location;
           locationChanged = true;
        }
        if(curLocation.getLatitude() == location.getLatitude() &&
              curLocation.getLongitude() == location.getLongitude())
           locationChanged = false;
        else
           locationChanged = true;
        curLocation = location;
    }

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

}
