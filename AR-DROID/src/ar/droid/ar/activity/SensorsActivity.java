package ar.droid.ar.activity;

import java.util.List;
import java.util.logging.Logger;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ar.droid.R;
import ar.droid.activity.EntityTabWidget;
import ar.droid.ar.common.ARData;
import ar.droid.ar.common.Matrix;
import ar.droid.ar.view.Marker;
import ar.droid.config.AppPreferences;
import ar.droid.location.LocationListenerGPSAR;
import ar.droid.model.Entity;
import ar.droid.resources.ImageHelperFactory;

public class SensorsActivity extends Activity implements SensorEventListener, OnTouchListener {
	private static final Logger logger = Logger.getLogger(SensorsActivity.class
			.getSimpleName());

	private static final float RTmp[] = new float[9];
	private static final float Rot[] = new float[9];
	private static final float grav[] = new float[3];
	private static final float mag[] = new float[3];

	private static int rHistIdx = 0;
	private static final Matrix tempR = new Matrix();
	private static final Matrix finalR = new Matrix();
	private static final Matrix smoothR = new Matrix();
	private static final Matrix histR[] = new Matrix[10];
	private static final Matrix m1 = new Matrix();
	private static final Matrix m2 = new Matrix();
	private static final Matrix m3 = new Matrix();
	private static final Matrix mageticNorthCompensation = new Matrix();

	private static SensorManager sensorMgr = null;
	private static List<Sensor> sensors = null;
	private static Sensor sensorGrav = null;
	private static Sensor sensorMag = null;

	private static LocationManager locationMgr = null;
	private LocationListenerGPSAR locationListener = null;
	protected TextView locationText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// pantalla completa
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	@Override
	public void onStart() {
		super.onStart();

		double angleX = Math.toRadians(-90);
		double angleY = Math.toRadians(-90);

		m1.set(1f, 0f, 0f, 0f, (float) Math.cos(angleX),
				(float) -Math.sin(angleX), 0f, (float) Math.sin(angleX),
				(float) Math.cos(angleX));

		m2.set(1f, 0f, 0f, 0f, (float) Math.cos(angleX),
				(float) -Math.sin(angleX), 0f, (float) Math.sin(angleX),
				(float) Math.cos(angleX));

		m3.set((float) Math.cos(angleY), 0f, (float) Math.sin(angleY), 0f, 1f,
				0f, (float) -Math.sin(angleY), 0f, (float) Math.cos(angleY));

		mageticNorthCompensation.toIdentity();

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

			sensorMgr.registerListener(this, sensorGrav,
					SensorManager.SENSOR_DELAY_UI);
			sensorMgr.registerListener(this, sensorMag,
					SensorManager.SENSOR_DELAY_UI);

			locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			int minTime = AppPreferences.getInt("gpsTimePref", 5000);
			int minDistance = AppPreferences.getInt("gpsDistPref", 5);

			if (locationListener == null) {
				Location location = locationMgr
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				locationListener = new LocationListenerGPSAR(this,
						locationText, location);
			}
			locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					minTime, minDistance, locationListener);

			locationListener.showAddress();

			try {
				Location hardFix = new Location("ATL");
				hardFix.setLatitude(-34.90357);
				hardFix.setLongitude(-57.93777);
				hardFix.setAltitude(40);

				try {
					Location gps = locationMgr
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					Location network = locationMgr
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (gps != null)
						locationListener.onLocationChanged(gps);
					else if (network != null)
						locationListener.onLocationChanged(network);
					else
						locationListener.onLocationChanged(hardFix);
				} catch (Exception ex2) {
					locationListener.onLocationChanged(hardFix);
				}

				GeomagneticField gmf = new GeomagneticField((float) ARData.getCurrentLocation()
						.getLatitude(), (float) ARData.getCurrentLocation().getLongitude(),
						(float) ARData.getCurrentLocation().getAltitude(), System
								.currentTimeMillis());
				angleY = Math.toRadians(-gmf.getDeclination());

				mageticNorthCompensation
						.set((float) Math.cos(angleY), 0f,
								(float) Math.sin(angleY), 0f, 1f, 0f,
								(float) -Math.sin(angleY), 0f,
								(float) Math.cos(angleY));

			} catch (Exception ex) {
				logger.info("Exception: " + ex);
			}
		} catch (Exception ex1) {
			try {
				if (sensorMgr != null) {
					sensorMgr.unregisterListener(this, sensorGrav);
					sensorMgr.unregisterListener(this, sensorMag);
					sensorMgr = null;
				}
				if (locationMgr != null) {
					locationMgr.removeUpdates((LocationListener) this);
					locationMgr = null;
				}
			} catch (Exception ex2) {
				logger.info("Exception: " + ex2);
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
				logger.info("Exception: " + ex);
			}
			sensorMgr = null;

			try {
				locationMgr.removeUpdates((LocationListener) this);
			} catch (Exception ex) {
				logger.info("Exception: " + ex);
			}
			locationMgr = null;
		} catch (Exception ex) {
			logger.info("Exception: " + ex);
		}
	}

	@Override
	public void onSensorChanged(SensorEvent evt) {

		if (evt.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			grav[0] = evt.values[0];
			grav[1] = evt.values[1];
			grav[2] = evt.values[2];
		} else if (evt.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			mag[0] = evt.values[0];
			mag[1] = evt.values[1];
			mag[2] = evt.values[2];
		}

		SensorManager.getRotationMatrix(RTmp, null, grav, mag);

		SensorManager.remapCoordinateSystem(RTmp, SensorManager.AXIS_X,
				SensorManager.AXIS_MINUS_Z, Rot);

		tempR.set(Rot[0], Rot[1], Rot[2], Rot[3], Rot[4], Rot[5], Rot[6],
				Rot[7], Rot[8]);

		finalR.toIdentity();
		finalR.prod(mageticNorthCompensation);
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
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD
				&& accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
			logger.info("Datos de brújula no fiables");
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		if (me.getAction() != MotionEvent.ACTION_UP)
			return false;
		
		final Entity entitySelected = this.getMarkerAt(me.getX(), me.getY());
		if(entitySelected == null)
			return false;
		
		final Dialog dialog = new Dialog(SensorsActivity.this);

		dialog.setContentView(R.layout.view_entity_ar);
		dialog.setTitle(entitySelected.getName());

		TextView text = (TextView) dialog.findViewById(R.id.text_e_ar);
		if(entitySelected.getDescription().length() > 150)
			text.setText(entitySelected.getDescription().substring(0, 150) + " ...");
		else
			text.setText(entitySelected.getDescription());
		ImageView image = (ImageView) dialog.findViewById(R.id.image_e_ar);
		image.setImageDrawable(ImageHelperFactory.createImageHelper().getImageEntity(entitySelected));
		
		Button button = (Button) dialog.findViewById(R.id.button_e_ar);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), EntityTabWidget.class);
				i.putExtra("idEntity", entitySelected.getId());
				startActivity(i);
				dialog.dismiss();
			}
		});
		dialog.setCancelable(true);
		dialog.show();
		return true;
	}

	private Entity getMarkerAt(float x, float y) {
		for (int i = 0 ; i < ARData.getMarkerCount(); i++) {
			Marker marker = ARData.getMarker(i);
			if(marker.clickedMe(x, y))
				return marker.getEntity();
		}
		return null;
	}
}
