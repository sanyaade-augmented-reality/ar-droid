package ar.droid.driving;

import com.google.android.maps.GeoPoint;

public interface IDrivingDirections {
	
	public RoutePath driveTo (GeoPoint startPoint, GeoPoint endPoint, Mode mode);
	
}
