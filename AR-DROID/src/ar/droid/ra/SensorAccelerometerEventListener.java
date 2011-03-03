package ar.droid.ra;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorAccelerometerEventListener implements SensorEventListener {

	 public static volatile float direction = (float) 0;
	 public static volatile float inclination;
	 public static volatile float rollingZ = (float)0;

	 public static volatile float kFilteringFactor = (float)0.05;
	 public static float aboveOrBelow = (float)0;

	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent evt) {
		 float vals[] = evt.values;
	      if(evt.sensor.getType() == Sensor.TYPE_ORIENTATION)  {
	         float rawDirection = vals[0];

	         direction =(float) ((rawDirection * kFilteringFactor) + 
	            (direction * (1.0 - kFilteringFactor)));

	          inclination = 
	            (float) ((vals[2] * kFilteringFactor) + 
	            (inclination * (1.0 - kFilteringFactor)));

	                
	          if(aboveOrBelow > 0)
	             inclination = inclination * -1;
	          
	         if(evt.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
	         {
	            aboveOrBelow =
	               (float) ((vals[2] * kFilteringFactor) + 
	               (aboveOrBelow * (1.0 - kFilteringFactor)));
	         }
	      }


	}

}
