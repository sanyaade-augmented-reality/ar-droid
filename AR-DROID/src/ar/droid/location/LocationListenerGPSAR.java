package ar.droid.location;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;
import ar.droid.MainAR;
import ar.droid.ar.common.ARData;

public class LocationListenerGPSAR implements LocationListener {
	static String TAG = LocationListenerGPSAR.class.getName();
	protected Location lastKnowLocation;
	protected MainAR activity;
	private TextView locationText;

	public LocationListenerGPSAR(Activity activity, TextView locationText, Location lastKnowLocation) {
		this.activity = (MainAR) activity;
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

		// mostrar ubicaci�n
		this.showAddress();
	}

	public void showAddress() {
		String addressName = "";

		if (lastKnowLocation == null)
			addressName = "Ubicaci�n desconocida";
		else {
			// obtener ubicaci�n

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
						addressName = "Ubicaci�n desconocida";
				}
			} catch (Exception e) {
				addressName = "Ubicaci�n desconocida";
			}
		}

		//sumar altitud
		DecimalFormat df = new DecimalFormat("0.#");
		addressName += "\n" + "Alt. " + df.format(lastKnowLocation.getAltitude()) + " mts.";
		
		// armar texto
		if (locationText != null) {
			locationText.setText(addressName);
			locationText.invalidate();
		}
	}
}
