package ar.droid.model;

import java.util.List;

import ar.droid.admin.survey.response.SurveyResponse;


public interface IResourceHelper {
	public List<Entity> getEntities();
	
	public List<Event> getEvents(Entity entity);
	
	public List<Event> getEvents();
	
	public List<Event> getEvents(String option);
	
	public void saveResponse(SurveyResponse surveyResponse);
}
