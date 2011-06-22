package ar.droid.admin.survey.question;

import com.google.gson.annotations.Expose;

public class Choice {
	
	private String description;
	
	@Expose
	private Long id;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
