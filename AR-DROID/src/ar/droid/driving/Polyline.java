package ar.droid.driving;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.gson.annotations.Expose;

public class Polyline {
	private String points;
	private String levels;	
	@Expose private List<GeoPoint> polylines = new ArrayList<GeoPoint>();
	
	public List<GeoPoint> getPolylines() {
		return polylines;
	}
	public void setPolylines(List<GeoPoint> polylines) {
		this.polylines = polylines;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getLevels() {
		return levels;
	}
	public void setLevels(String levels) {
		this.levels = levels;
	}
	
	public void addToPolyline(GeoPoint geoPoint){
		getPolylines().add(geoPoint);
	}
		
}
