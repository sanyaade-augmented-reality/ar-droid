package ar.droid;

import java.util.List;
import java.util.logging.Logger;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import ar.droid.activity.EntityTabWidget;
import ar.droid.ar.activity.AugmentedView;
import ar.droid.ar.activity.SensorsActivity;
import ar.droid.ar.camara.CameraSurface;
import ar.droid.ar.common.ARData;
import ar.droid.ar.common.DataSource;
import ar.droid.ar.view.Marker;
import ar.droid.config.ARDROIDProperties;
import ar.droid.model.Entity;

public class MainAR extends SensorsActivity implements OnTouchListener {
	static String TAG = MainAR.class.getName();
	private static final Logger logger = Logger.getLogger(MainAR.class.getSimpleName());

	private static DataSource source = null;

	private static CameraSurface camScreen = null;
	private static AugmentedView augmentedView = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		camScreen = new CameraSurface(this);
		setContentView(camScreen);
		
		// crear layout
		LayoutParams augLayout = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		
		augmentedView = new AugmentedView(this);
		addContentView(augmentedView, augLayout);
		
		// agragar texto de posición
		locationText = new TextView(this);
		locationText.setTextColor(Color.RED);
		locationText.setPadding(0, 10, 10, 0);
		locationText.setTextSize(15);
		locationText.setGravity(Gravity.RIGHT);
		locationText.bringToFront();
		addContentView(locationText, augLayout);

		// Setear el alcance de realidad
		updateDataOnZoom();
		
		// crear el source
		if (source == null) {
			source = new DataSource();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		logger.info("onDestroy()");
	}

	@Override
	public void onStart() {
		super.onStart();

		Location lastLocation = ARData.getCurrentLocation();
		if (lastLocation != null)
			updateData(lastLocation.getLatitude(), lastLocation.getLongitude(), lastLocation.getAltitude());
	}

	@Override
	public void onSensorChanged(SensorEvent evt) {
		super.onSensorChanged(evt);

		if (evt.sensor.getType() == Sensor.TYPE_ACCELEROMETER
				|| evt.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			augmentedView.postInvalidate();
		}
	}

	private static float calcZoomLevel() {
		int zoomLevel = ARDROIDProperties.getInstance().getIntProperty("ar.droid.ar.zoomlevel");
		float myout = 5;

		if (zoomLevel <= 26) {
			myout = zoomLevel / 25f;
		} else if (25 < zoomLevel && zoomLevel < 50) {
			myout = (1 + (zoomLevel - 25)) * 0.38f;
		} else if (25 == zoomLevel) {
			myout = 1;
		} else if (50 == zoomLevel) {
			myout = 10;
		} else if (50 < zoomLevel && zoomLevel < 75) {
			myout = (10 + (zoomLevel - 50)) * 0.83f;
		} else {
			myout = (30 + (zoomLevel - 75) * 2f);
		}

		return myout;
	}

	protected void updateDataOnZoom() {
		float zoomLevel = calcZoomLevel();
		ARData.setRadius(zoomLevel);
		ARData.setZoomLevel(String.valueOf(zoomLevel));

		int defZoomLevel = ARDROIDProperties.getInstance().getIntProperty("ar.droid.ar.zoomlevel");
		ARData.setZoomProgress(defZoomLevel);
	};

	private static Thread thread = null;

	public void updateData(final double lat, final double lon, final double alt) {
		if (thread != null && thread.isAlive()) {
			logger.info("Hay una instancia de actuaización en curso");
			return;
		}

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				download(source, lat, lon, alt);
				logger.info("Termina actualización");
			}
		});
		thread.start();
	}

	private static boolean download(DataSource source, double lat, double lon, double alt) {
		if (source == null)
			return false;

		List<Marker> markers = source.getEntities();
		if (markers == null)
			return false;

		logger.info(source.getClass().getSimpleName() + " size=" + markers.size());

		ARData.addMarkers(markers);
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_ar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_mapa:

			// volver al mapa
			this.finish();
			return true;
		case R.id.menu_find:
			return true;
		case R.id.menu_show:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		case R.id.menu_close:
			this.exit();
			return true;
		}
	}

	private void exit() {
		Log.d(TAG, "Salir");
		int pid = android.os.Process.myPid();
		android.os.Process.killProcess(pid);
	}

	@Override
	public boolean onTouch(View view, MotionEvent me) {
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent me) {
		if (me.getAction() != MotionEvent.ACTION_UP)
			return false;
		
		Entity entity = this.getMarkerAt(me.getX(), me.getY());
		if(entity == null)
			return false;
		Intent i = new Intent(this.getApplicationContext(), EntityTabWidget.class);
		i.putExtra("idEntity", entity.getId());
		this.startActivity(i);
		finish();
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
