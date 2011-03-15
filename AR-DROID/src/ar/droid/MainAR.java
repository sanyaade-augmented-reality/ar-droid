package ar.droid;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import ar.droid.ra.SensorAccelerometerEventListener;
import ar.droid.ra.SurfaceHolderListener;
import ar.droid.ra.view.Radar;


public class MainAR extends Activity {
	static String TAG = MainAR.class.getName();
	public static SensorManager sensorMan;
	
	private SurfaceView preview;
	private SurfaceHolder previewHolder;
	private Camera camera;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // pantalla completa
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // setear layout
        setContentView(R.layout.mainar);
        
        // inicializar camara
        this.initCamera();

        // agregar radar
        this.addRadar();
	}

	/**
	 * agrega el radar a la superficie de la camara
	 */
	private void addRadar(){	 
	  	RelativeLayout infoLayer = new RelativeLayout(getApplicationContext());
	  	infoLayer.setPadding(10, 10, 0, 0);
	  	addContentView(infoLayer, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
	  	infoLayer.addView(new Radar(getApplicationContext()), new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	}
	
	/**
	 * crea e inicializa la captura de video.
	 * ademas inicia el sensor de movimiento del dispositivo
	 */
	private void initCamera() {
		preview = (SurfaceView) findViewById(R.id.cameraPreview);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(new SurfaceHolderListener(camera, previewHolder));
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        // iniciar sensor
        sensorMan = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		sensorMan.registerListener(new SensorAccelerometerEventListener(), sensorMan.getDefaultSensor(SensorManager.SENSOR_ORIENTATION), SensorManager.SENSOR_DELAY_FASTEST);
		sensorMan.registerListener(new SensorAccelerometerEventListener(), sensorMan.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
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