package ar.droid.driving;

import com.google.android.maps.GeoPoint;


public abstract class DrivingDirections implements IDrivingDirections{
	
	protected abstract void startDrivingTo (GeoPoint startPoint, GeoPoint endPoint, Mode mode,IDirectionsListener directionsListener); 
	
	public void driveTo (GeoPoint startPoint, GeoPoint endPoint, Mode mode,IDirectionsListener directionsListener){
		if ((startPoint == null) || (endPoint == null) || (mode == null)) {
			throw new IllegalArgumentException ("startPoint, endPoint or mode arguments can't be null");
		}
		startDrivingTo(startPoint, endPoint, mode,directionsListener);
	}
	
	
	protected void directionsAvailable (IDirectionsListener directionsListener,Route route){
		if (directionsListener != null) {
			directionsListener.directionAvailable(route);
		}
	}
	
	protected void directionsNotAvailable (IDirectionsListener directionsListener){
		if (directionsListener != null) {
			directionsListener.directionNotAvailable();
		}
	}
}
