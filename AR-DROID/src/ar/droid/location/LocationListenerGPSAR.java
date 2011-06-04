package ar.droid.location;

import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;
import ar.droid.MainAR;
import ar.droid.ar.common.ARData;

public class LocationListenerGPSAR implements LocationListener {
	static int VIEW_ADDRESS = 100000000;
	static String TAG = LocationListenerGPSAR.class.getName();
	protected Location lastKnowLocation;
	protected MainAR activity;
	private TextView locationText;

	public LocationListenerGPSAR(MainAR activity, TextView locationText, Location lastKnowLocation) {
		this.activity = activity;
		this.lastKnowLocation = lastKnowLocation;
		this.locationText = locationText;
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

	@Override
	public void onLocationChanged(Location location) {
		ARData.setCurrentLocation(location);
		this.lastKnowLocation = location;
		this.activity.updateData(location.getLatitude(), location.getLongitude(), location
				.getAltitude());

		// mostrar ubicación
		this.showAddress();
	}

	public void showAddress() {
		String addressName = "";

		if (lastKnowLocation == null)
			addressName = "Ubicación desconocida";
		else {
			// obtener ubicación

			try {
				Geocoder geocoder = new Geocoder(this.activity, Locale.getDefault());
				List<Address> lsAddress = geocoder.getFromLocation(lastKnowLocation.getLatitude(),
						lastKnowLocation.getLongitude(), 1);
				if (lsAddress.size() > 0) {
					Address address = lsAddress.get(0);
					if (address.getAddressLine(0) != null)
						addressName = address.getAddressLine(0);
					else if (address.getThoroughfare() != null)
						addressName = address.getThoroughfare();
					else if (address.getFeatureName() != null)
						addressName = address.getFeatureName();
					else if (address.getLocality() != null)
						addressName = address.getLocality();
					else
						addressName = "Ubicación desconocida";
				}
			} catch (Exception e) {
				addressName = "Ubicación desconocida";
			}
		}

		// armar texto
		if (locationText != null) {
			locationText.setText(addressName);
			locationText.invalidate();
		}
	}
}
