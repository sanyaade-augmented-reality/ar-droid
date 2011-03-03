package ar.droid;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import ar.droid.ra.CustomCameraView;
import ar.droid.ra.SensorAccelerometerEventListener;
import ar.droid.ra.view.Radar;

public class TestCamera extends Activity {
	private CustomCameraView customCameraView;
	public static SensorManager sensorMan;
	
	public void onCreate(Bundle savedInstanceState) {
		 try{
			 super.onCreate(savedInstanceState);
			 customCameraView = new CustomCameraView(getApplicationContext());
		     RelativeLayout rl = new RelativeLayout(getApplicationContext());
		     setContentView(rl);
		     rl.addView(customCameraView);
		  	 RelativeLayout infoLayer = new RelativeLayout(getApplicationContext());
 		  	 rl.addView(infoLayer, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
 		  	 infoLayer.addView(new Radar(getApplicationContext()), new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		  	//rl.addView(new Radar(getApplicationContext()));

		   
		      
		   } catch(Exception e){
			   Log.e("error", "error", e);
		   }
		   
		   sensorMan = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		   sensorMan.registerListener(new SensorAccelerometerEventListener(), sensorMan.getDefaultSensor(SensorManager.SENSOR_ORIENTATION), SensorManager.SENSOR_DELAY_FASTEST);
		   sensorMan.registerListener(new SensorAccelerometerEventListener(), sensorMan.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
		   
		   LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,1, new ar.droid.location.LocationListenerGPS());
	    
	}
	

}
