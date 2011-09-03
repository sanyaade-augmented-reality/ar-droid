package ar.droid.admin.survey.response;

import java.util.ArrayList;
import java.util.List;

public class Summary {
	private String description;
	private Float rating;
	private List<String>comments = new ArrayList<String>();
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	
	
	
}
