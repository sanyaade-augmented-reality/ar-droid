package ar.droid.location;

import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MyLocationOverlayFirstRun implements Runnable {
	static String TAG = MyLocationOverlayFirstRun.class.getName();
	
	private MyLocationOverlay myLocationOverlay;
	private MapView mapView;
	
	public MyLocationOverlayFirstRun(MapView map, MyLocationOverlay locationOverlay){
		mapView = map;
		myLocationOverlay = locationOverlay;
	}
	
	@Override
	public void run() {
		if(myLocationOverlay.getMyLocation() != null){
			mapView.getController().animateTo(myLocationOverlay.getMyLocation());
    		mapView.getController().setZoom(17);
		}
    }

}
