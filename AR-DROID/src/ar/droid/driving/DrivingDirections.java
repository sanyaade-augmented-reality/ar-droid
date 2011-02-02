package ar.droid.driving;

import com.google.android.maps.GeoPoint;

public abstract class DrivingDirections implements IDrivingDirections{
	
	protected abstract RoutePath startDrivingTo (GeoPoint startPoint, GeoPoint endPoint, Mode mode); 
	
	public RoutePath driveTo (GeoPoint startPoint, GeoPoint endPoint, Mode mode){
		if ((startPoint == null) || (endPoint == null) || (mode == null)) {
			throw new IllegalArgumentException ("startPoint, endPoint or mode arguments can't be null");
		}
		return startDrivingTo(startPoint, endPoint, mode);
	}
}
