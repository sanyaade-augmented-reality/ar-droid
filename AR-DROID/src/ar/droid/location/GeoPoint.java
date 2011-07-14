package ar.droid.location;

public class GeoPoint extends com.google.android.maps.GeoPoint implements Comparable<GeoPoint> {
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

	@Override
	public int compareTo(GeoPoint another) {
		if (another.getLatitudeE6() == this.getLatitudeE6() && another.getLongitudeE6() == this.getLongitudeE6()){
			return 0;
		}
		return 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp,temp2;
		temp = Double.doubleToLongBits(getLatitudeE6());
		temp2 = Double.doubleToLongBits(getLongitudeE6());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (temp2 ^ (temp2 >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeoPoint other = (GeoPoint) obj;
		if (Double.doubleToLongBits(getLongitudeE6()) != Double.doubleToLongBits(other.getLongitudeE6()) && 
				Double.doubleToLongBits(getLatitudeE6()) != Double.doubleToLongBits(other.getLatitudeE6()))
			return false;
		return true;
	}

	
	
	

}
