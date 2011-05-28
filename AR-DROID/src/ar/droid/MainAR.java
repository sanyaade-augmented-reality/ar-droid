package ar.droid;

import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import ar.droid.ar.activity.AugmentedView;
import ar.droid.ar.activity.SensorsActivity;
import ar.droid.ar.camara.CameraSurface;
import ar.droid.ar.common.ARData;
import ar.droid.ar.common.DataSource;
import ar.droid.ar.view.Marker;
import ar.droid.common.ARDroidSource;
import ar.droid.config.ARDROIDProperties;

public class MainAR extends SensorsActivity {
	static String TAG = MainAR.class.getName();
	private static final Logger logger = Logger.getLogger(MainAR.class.getSimpleName());
	private static final String locale = Locale.getDefault().getLanguage();
	
	private static DataSource source = null;
	
	private static CameraSurface camScreen = null;    
    private static AugmentedView augmentedView = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.info("onCreate()");

        camScreen = new CameraSurface(this);
        setContentView(camScreen);

        augmentedView = new AugmentedView(this);
        LayoutParams augLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addContentView(augmentedView, augLayout);
        
        updateDataOnZoom();
             
        // crear el source
        if (source == null) {
        	source = new ARDroidSource();
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
    	logger.info("onStart()");
    	
    	Location last = ARData.getCurrentLocation();
        if (last!=null) updateData(last.getLatitude(),last.getLongitude(),last.getAltitude());
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	logger.info("onStop()");
    }
    
    @Override
    public void onSensorChanged(SensorEvent evt) {
        super.onSensorChanged(evt);

        if (    evt.sensor.getType() == Sensor.TYPE_ACCELEROMETER || 
                evt.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD
        ) {
            augmentedView.postInvalidate();
        }
    }
    
    private static float calcZoomLevel(){
        int zoomLevel = ARDROIDProperties.getInstance().getIntProperty("ar.droid.ar.zoomlevel");
        float myout = 5;
    
        if (zoomLevel <= 26) {
            myout = zoomLevel / 25f;
        } else if (25 < zoomLevel && zoomLevel < 50) {
            myout = (1 + (zoomLevel - 25)) * 0.38f;
        } else if (25== zoomLevel) {
            myout = 1;
        } else if (50== zoomLevel) {
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
    
    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        updateData(location.getLatitude(),location.getLongitude(),location.getAltitude());
    }
    
    private static Thread thread = null;
    private void updateData(final double lat, final double lon, final double alt) {
    	if (thread!=null && thread.isAlive()) {
    		logger.info("Not updating since in the process");
    		return;
    	}
    	
    	thread = new Thread(
    		new Runnable(){
				@Override
				public void run() {
					logger.info("empieza");
					download(source, lat, lon, alt);
					logger.info("termina");
				}
			}
    	);
    	thread.start();
    }
    
    private static boolean download(DataSource source, double lat, double lon, double alt) {
		if (source == null) return false;
		
		String url = source.createRequestURL(lat, lon, alt, ARData.getRadius(), locale);    	
    	logger.info(url);
    	if (url==null) return false;
    	
    	List<Marker> markers = source.parse(url);
    	if (markers==null) return false;
    	
    	logger.info(source.getClass().getSimpleName()+" size="+markers.size());
    	
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
		    	
		    	// terminar la aplicacion
		    	Log.d(TAG, "Salir");
		    	int pid = android.os.Process.myPid(); 
		    	android.os.Process.killProcess(pid); 
		        return true;
	    }
	}
}
