package ar.droid.driving;

import java.util.ArrayList;
import java.util.List;
import ar.droid.location.GeoPoint;

public class Route {
	
	//private String status;
	
	private String summary;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	private List<Leg> legs = new ArrayList<Leg>();

	public List<Leg> getLegs() {
		return legs;
	}

	public void setLegs(List<Leg> legs) {
		this.legs = legs;
	}

	/*public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}*/

	//@SerializedName("start_address") private  String startAddress;
	//@SerializedName("end_address") private  String endtAddress;
	//@SerializedName("end_location") private GeoPoint endLocation;
	//@SerializedName("start_location") private GeoPoint startLocation;

	/*public String getStartAddress() {
		return startAddress;
	}
	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}
	public String getEndtAddress() {
		return endtAddress;
	}
	public void setEndtAddress(String endtAddress) {
		this.endtAddress = endtAddress;
	}*/
	/*public GeoPoint getEndLocation() {
		return endLocation;
	}
	public void setEndLocation(GeoPoint endLocation) {
		this.endLocation = endLocation;
	}
	public GeoPoint getStartLocation() {
		return startLocation;
	}
	public void setStartLocation(GeoPoint startLocation) {
		this.startLocation = startLocation;
	}*/
	
	
}
