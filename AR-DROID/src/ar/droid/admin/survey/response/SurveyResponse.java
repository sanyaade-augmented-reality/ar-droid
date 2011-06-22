package ar.droid.admin.survey.response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import ar.droid.model.Event;

public class SurveyResponse {
	@Expose
	private Event event;
	@Expose
	private List<Response> responses;
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public List<Response> getResponses() {
		return responses;
	}
	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}
	
	public void addResponse(Response response){
		if (responses == null){
			responses = new ArrayList<Response>();
		}
		responses.add(response);
	}
}
