package ar.droid.location;

public class GeoPoint extends com.google.android.maps.GeoPoint {
	private double altitude;
	
	public GeoPoint(com.google.android.maps.GeoPoint geoPoint) {
		super(geoPoint.getLatitudeE6(), geoPoint.getLongitudeE6());
		this.setAltitude(0.00);
	}
	
	public GeoPoint(int latitudeE6, int longitudeE6) {
		super(latitudeE6, longitudeE6);
		this.setAltitude(0.00);
	}
	
	public GeoPoint(int latitudeE6, int longitudeE6, double altitude) {
		super(latitudeE6, longitudeE6);
		this.setAltitude(altitude);
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getAltitude() {
		return altitude;
	}

}
