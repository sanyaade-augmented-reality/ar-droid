package ar.droid.driving;

import ar.droid.location.GeoPoint;

public interface IDrivingDirections {
	
	public RoutePath driveTo (GeoPoint startPoint, GeoPoint endPoint, Mode mode);
	
}
