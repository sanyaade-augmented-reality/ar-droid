package ar.droid.ar.activity;

import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import ar.droid.ar.common.ARData;
import ar.droid.ar.common.Matrix;
import ar.droid.config.ARDROIDProperties;

public class SensorsActivity extends Activity implements SensorEventListener, LocationListener {
	private static final Logger logger = Logger.getLogger(SensorsActivity.class.getSimpleName());

	private static float RTmp[] = new float[9];
	private static float Rot[] = new float[9];
	private static float I[] = new float[9];
	private static float grav[] = new float[3];
	private static float mag[] = new float[3];

	private static int rHistIdx = 0;
	private static Matrix tempR = new Matrix();
	private static Matrix finalR = new Matrix();
	private static Matrix smoothR = new Matrix();
	private static Matrix histR[] = new Matrix[60];
	private static Matrix m1 = new Matrix();
	private static Matrix m2 = new Matrix();
	private static Matrix m3 = new Matrix();
	private static Matrix m4 = new Matrix();

	private static SensorManager sensorMgr = null;
	private static List<Sensor> sensors = null;
	private static Sensor sensorGrav = null;
	private static Sensor sensorMag = null;
	private static LocationManager locationMgr = null;

	private static int updateSensorCounter = 0;
	private static int updateSensorMax = 10;

	private TextView locationText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// pantalla completa
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// agragar texto de posición
		locationText = new TextView(this);
		locationText.setTextColor(Color.RED);
		locationText.setPadding(0, 10, 10, 0);
		locationText.setTextSize(15);
		locationText.setGravity(Gravity.RIGHT);
		
		LayoutParams augLayout = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		addContentView(locationText, augLayout);
	}

	@Override
	public void onStart() {
		super.onStart();

		double angleX = Math.toRadians(-90);
		double angleY = Math.toRadians(-90);

		m1.set(1f, 0f, 0f, 0f, (float) Math.cos(angleX), (float) -Math.sin(angleX), 0f,
				(float) Math.sin(angleX), (float) Math.cos(angleX));

		m2.set(1f, 0f, 0f, 0f, (float) Math.cos(angleX), (float) -Math.sin(angleX), 0f,
				(float) Math.sin(angleX), (float) Math.cos(angleX));

		m3.set((float) Math.cos(angleY), 0f, (float) Math.sin(angleY), 0f, 1f, 0f, (float) -Math
				.sin(angleY), 0f, (float) Math.cos(angleY));

		m4.toIdentity();

		for (int i = 0; i < histR.length; i++) {
			histR[i] = new Matrix();
		}

		try {
			sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

			sensors = sensorMgr.getSensorList(Sensor.TYPE_ACCELEROMETER);
			if (sensors.size() > 0)
				sensorGrav = sensors.get(0);

			sensors = sensorMgr.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
			if (sensors.size() > 0)
				sensorMag = sensors.get(0);

			sensorMgr.registerListener(this, sensorGrav, SensorManager.SENSOR_DELAY_GAME);
			sensorMgr.registerListener(this, sensorMag, SensorManager.SENSOR_DELAY_GAME);

			locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			int minTime = ARDROIDProperties.getInstance().getIntProperty("ar.droid.gps.mintime");
			int minDistance = ARDROIDProperties.getInstance().getIntProperty(
					"ar.droid.gps.mindistance");
			locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance,
					this);

			this.showAddress();

			try {
				Location hardFix = new Location("ATL");
				hardFix.setLatitude(39.931261);
				hardFix.setLongitude(-75.051267);
				hardFix.setAltitude(1);

				try {
					Location gps = locationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					Location network = locationMgr
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (gps != null)
						onLocationChanged(gps);
					else if (network != null)
						onLocationChanged(network);
					else
						onLocationChanged(hardFix);
				} catch (Exception ex2) {
					onLocationChanged(hardFix);
				}

				GeomagneticField gmf = new GeomagneticField((float) ARData.getCurrentLocation()
						.getLatitude(), (float) ARData.getCurrentLocation().getLongitude(),
						(float) ARData.getCurrentLocation().getAltitude(), System
								.currentTimeMillis());
				angleY = Math.toRadians(-gmf.getDeclination());

				m4.set((float) Math.cos(angleY), 0f, (float) Math.sin(angleY), 0f, 1f, 0f,
						(float) -Math.sin(angleY), 0f, (float) Math.cos(angleY));

			} catch (Exception ex) {
				logger.info("ERROR en Localización : " + ex);
			}
		} catch (Exception ex1) {
			try {
				if (sensorMgr != null) {
					sensorMgr.unregisterListener(this, sensorGrav);
					sensorMgr.unregisterListener(this, sensorMag);
					sensorMgr = null;
				}
				if (locationMgr != null) {
					locationMgr.removeUpdates(this);
					locationMgr = null;
				}
			} catch (Exception ex2) {
				logger.info("ERROR en Localización : " + ex2);
			}
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

		try {
			try {
				sensorMgr.unregisterListener(this, sensorGrav);
			} catch (Exception ex) {
				logger.info("Exception: " + ex);
			}
			try {
				sensorMgr.unregisterListener(this, sensorMag);
			} catch (Exception ex) {
				logger.info("ERROR en Sensor : " + ex);
			}
			sensorMgr = null;

			try {
				locationMgr.removeUpdates(this);
			} catch (Exception ex) {
				logger.info("ERROR en Localización : " + ex);
			}
			locationMgr = null;
		} catch (Exception ex) {
			logger.info("ERROR : " + ex);
		}
	}

	@Override
	public void onSensorChanged(SensorEvent evt) {
		if (updateSensorCounter > updateSensorMax) {
			updateSensorCounter = 0;
			return;
		}

		updateSensorCounter++;
		if (evt.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			grav[0] = evt.values[0];
			grav[1] = evt.values[1];
			grav[2] = evt.values[2];
		} else if (evt.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			mag[0] = evt.values[0];
			mag[1] = evt.values[1];
			mag[2] = evt.values[2];
		}

		SensorManager.getRotationMatrix(RTmp, I, grav, mag);
		SensorManager.remapCoordinateSystem(RTmp, SensorManager.AXIS_X, SensorManager.AXIS_MINUS_Z,
				Rot);

		tempR.set(Rot[0], Rot[1], Rot[2], Rot[3], Rot[4], Rot[5], Rot[6], Rot[7], Rot[8]);

		finalR.toIdentity();
		finalR.prod(m4);
		finalR.prod(m1);
		finalR.prod(tempR);
		finalR.prod(m3);
		finalR.prod(m2);
		finalR.invert();

		histR[rHistIdx].set(finalR);
		rHistIdx++;
		if (rHistIdx >= histR.length)
			rHistIdx = 0;

		smoothR.set(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
		for (int i = 0; i < histR.length; i++) {
			smoothR.add(histR[i]);
		}
		smoothR.mult(1 / (float) histR.length);

		ARData.setRotationMatrix(smoothR);
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

		// mostrar ubicación
		this.showAddress();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD
				&& accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
			logger.info("Brújula DE datos poco fiable!");
		}
	}

	protected void showAddress() {
		Location currentLocation = ARData.getCurrentLocation();
		String addressName = "";

		if (currentLocation == null)
			addressName = "Ubicación desconocida";
		else {
			// obtener ubicación

			try {
				Geocoder geocoder = new Geocoder(this, Locale.getDefault());
				List<Address> lsAddress = geocoder.getFromLocation(currentLocation.getLatitude(),
						currentLocation.getLongitude(), 1);
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
		if(locationText != null){
			locationText.setText(addressName);
			locationText.bringToFront();
			locationText.invalidate();
		}
	}
}
