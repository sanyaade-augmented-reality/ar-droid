package ar.droid.location;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import ar.droid.R;

public class LocationListenerGPS implements LocationListener {
	static int VIEW_ADDRESS = 100000000;
	static String TAG = "LocationListenerGPS";
	protected Location lastKnowLocation;
	protected Context context;
	private Activity activity;

	public LocationListenerGPS(Context context, Activity activity){
		this.context = context;
		this.activity = activity;
	}
	
	@Override
	public void onLocationChanged(Location location) {
    	Log.d(TAG, "Posición recibida de GPS");
    	lastKnowLocation = location;
    	
    	this.showAddress();
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
	
	public Context getContext() {
		return context;
	}

	protected void showAddress() {
		if(lastKnowLocation == null)
			return;
		
		// obtener ubicación
		String addressName = "";
		try{
			Geocoder geocoder = new Geocoder(this.getContext(), Locale.getDefault());
			List<Address> lsAddress = geocoder.getFromLocation(lastKnowLocation.getLatitude(), lastKnowLocation.getLongitude(), 1);
			if(lsAddress.size() > 0){
				Address address = lsAddress.get(0);
				if(address.getAddressLine(0) != null)
					addressName = address.getAddressLine(0);
				else if(address.getThoroughfare() != null)
					addressName = address.getThoroughfare();
				else if(address.getFeatureName() != null)
					addressName = address.getFeatureName();
				else if(address.getLocality() != null)
					addressName = address.getLocality();
				else
					addressName = "Ubicación desconocida";
			}
		}
		catch (Exception e) {
			addressName = "Ubicación desconocida";
		}
		// armar texto
		TextView textView = (TextView) activity.findViewById(R.id.address);
		textView.setText(addressName);
		textView.invalidate();
	}
}
