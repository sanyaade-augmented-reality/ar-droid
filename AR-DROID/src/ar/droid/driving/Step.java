package ar.droid.driving;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {
	
	@SerializedName("travel_mode") private Mode travelMode;
	@SerializedName("html_instructions")  private String htmlInstructions;
	@Expose private Polyline polyline;
	
	public Polyline getPolyline() {
		return polyline;
	}
	public void setPolyline(Polyline polyline) {
		this.polyline = polyline;
	}
	public Mode getTravelMode() {
		return travelMode;
	}
	public void setTravelMode(Mode travelMode) {
		this.travelMode = travelMode;
	}
	public String getHtmlInstructions() {
		return htmlInstructions;
	}
	public void setHtmlInstructions(String htmlInstructions) {
		this.htmlInstructions = htmlInstructions;
	}
	
}
