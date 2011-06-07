package ar.droid.driving;

import com.google.android.maps.GeoPoint;



public interface IDrivingDirections {
	
	public void driveTo (GeoPoint startPoint, GeoPoint endPoint, Mode mode,IDirectionsListener directionsListener);
	
}
