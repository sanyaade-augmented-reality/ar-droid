package ar.droid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.location.Location;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import ar.droid.ar.activity.AugmentedView;
import ar.droid.ar.activity.SensorsActivity;
import ar.droid.ar.camara.CameraSurface;
import ar.droid.ar.common.ARData;
import ar.droid.ar.common.ARDroidDataSource;
import ar.droid.ar.common.DataSource;
import ar.droid.ar.view.IconMarker;
import ar.droid.ar.view.Marker;
import ar.droid.config.AppPreferences;
import ar.droid.model.Entity;
import ar.droid.resources.ImageHelperFactory;
import ar.droid.sound.SoundManager;

/**
 * @author gabriel
 *
 */
public class MainAR extends SensorsActivity {
	static int RESULT_BACK = 10101;
	private static final Logger logger = Logger.getLogger(MainAR.class
			.getSimpleName());

	private static Collection<DataSource> sources = null;
	private static Thread thread = null;

	private static WakeLock wakeLock = null;
	private static CameraSurface camScreen = null;
	private static AugmentedView augmentedView = null;
	
	private static List<Marker> markers = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		camScreen = new CameraSurface(this);
		setContentView(camScreen);

		augmentedView = new AugmentedView(this);
		LayoutParams augLayout = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		addContentView(augmentedView, augLayout);
		
		Runnable thread = new Runnable() {
			@Override
			public void run() {
				// agragar texto de posición
				locationText = new TextView(getApplicationContext());
				locationText.setTextColor(Color.RED);
				locationText.setPadding(0, 10, 10, 0);
				locationText.setTextSize(15);
				locationText.setGravity(Gravity.RIGHT);
				locationText.bringToFront();
				LayoutParams textLayout = new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT);
				addContentView(locationText, textLayout);
		
				// Setear el alcance de realidad
				updateDataOnZoom();
		
				PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
				wakeLock = pm
						.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
		
				if (sources == null) {
					sources = new ArrayList<DataSource>();
		
					DataSource ardroidDS = new ARDroidDataSource();
					sources.add(ardroidDS);
				}
			}
		}; 
		runOnUiThread(thread);
	}

	@Override
	public void onStart() {
		super.onStart();

		Location last = ARData.getCurrentLocation();
		if (last != null)
			updateData(last.getLatitude(), last.getLongitude(),
					last.getAltitude());
	}

	public void updateData(final double lat, final double lon, final double alt) {
		if (thread != null && thread.isAlive()) {
			logger.info("Hay una actualización en progreso");
			return;
		}

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("actualización - Start");
				for (DataSource source : sources) {
					download(source, lat, lon, alt);
				}
				logger.info("actualización - Stop");
			}
		});
		thread.start();
	}

	private static boolean download(DataSource source, double lat, double lon,
			double alt) {
		if (source == null)
			return false;

		if(markers == null){
			// obtener entidades
			List<Entity> entities = source.getEntities(lat, lon, alt);
			if (entities == null)
				return false;

			// convertir a markers
			markers = convertToMarkers(entities);
			ARData.addMarkers(markers);
		}
		return true;
	}

	private static List<Marker> convertToMarkers(List<Entity> entities) {
		List<Marker> markers = new ArrayList<Marker>();
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) {
			Entity entity = (Entity) it.next();
			Drawable iconTypeEntity = ImageHelperFactory.createImageHelper()
					.getIconTypeEntity(entity.getTypeEntity());
			
			// crear un marker a partir de la entidad
			Marker marker = new IconMarker(entity, scaleImage(iconTypeEntity));
			markers.add(marker);
		}
		return markers;
	}
	
	/**
	 * Redimensionar un BitmapDrawable
	 * @param drawable drawable a escalar
	 * @param scale escala (1 = 100%)
	 * @return
	 */
	private static Drawable scaleImage(Drawable drawable) {
		float scale = AppPreferences.getInt("iconSizePref", 2);
		if(scale == 1){
			scale = (float) 0.75;
		}
		else if(scale == 2){
			scale = (float) 1.25;
		}
		else if(scale == 3){
			scale = (float) 1.75;
		}	
		
		BitmapDrawable bdImage = (BitmapDrawable) drawable;
		Bitmap bitmapOrig = bdImage.getBitmap();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrig, 0, 0, bitmapOrig.getWidth(), bitmapOrig.getHeight(), matrix, true);
        BitmapDrawable bitmapDrawableResized = new BitmapDrawable(resizedBitmap);
        return bitmapDrawableResized; 
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_ar, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		SoundManager.playSound(R.raw.action);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SoundManager.playSound(R.raw.button);
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_mapa:
			// volver al mapa
			setResult(RESULT_CANCELED);
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		wakeLock.acquire();
	}

	@Override
	public void onPause() {
		super.onPause();
		wakeLock.release();
	}

	@Override
	public void onSensorChanged(SensorEvent evt) {
		super.onSensorChanged(evt);

		if (evt.sensor.getType() == Sensor.TYPE_ACCELEROMETER
				|| evt.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			augmentedView.postInvalidate();
		}
	}

	protected void updateDataOnZoom() {
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK){
			Bundle bundle = new Bundle();
			bundle.putLong("idEntity", entitySelected.getId());
			Intent mIntent = new Intent();
            mIntent.putExtras(bundle);
    		setResult(RESULT_OK, mIntent);
			finishFromChild(this);
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setResult(RESULT_CANCELED);
	}
}
