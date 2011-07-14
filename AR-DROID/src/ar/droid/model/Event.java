package ar.droid.model;

import ar.droid.admin.calendar.EventCalendar;
import ar.droid.admin.survey.SurveyTemplate;
import ar.droid.admin.survey.response.SurveyResponse;
import ar.droid.location.GeoPoint;

import com.google.gson.annotations.Expose;

public class Event {
	@Expose
	private Long id;
	private String title;
	private String description;
	private TypeEvent typeEvent;
	private GeoPoint geoPoint;
	private EventCalendar eventCalendar;
	private SurveyTemplate surveyTemplate;
	private String place;
	
	private Entity entity;
	private Long idEntity;
	
	public Long getIdEntity() {
		return idEntity;
	}

	public void setIdEntity(Long idEntity) {
		this.idEntity = idEntity;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	private SurveyResponse surveyResponse;
	
	
	public SurveyResponse getSurveyResponse() {
		return surveyResponse;
	}

	public void setSurveyResponse(SurveyResponse surveyResponse) {
		this.surveyResponse = surveyResponse;
	}

	public SurveyTemplate getSurveyTemplate() {
		return surveyTemplate;
	}

	public void setSurveyTemplate(SurveyTemplate surveyTemplate) {
		this.surveyTemplate = surveyTemplate;
	}

	public Event() {
	
	}
	
	public Event(Long id) {
		setId(id);
	}
	
	public EventCalendar getEventCalendar() {
		return eventCalendar;
	}
	public void setEventCalendar(EventCalendar eventCalendar) {
		this.eventCalendar = eventCalendar;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public TypeEvent getTypeEvent() {
		return typeEvent;
	}
	public void setTypeEvent(TypeEvent typeEvent) {
		this.typeEvent = typeEvent;
	}
	public GeoPoint getGeoPoint() {
		return geoPoint;
	}
	public void setGeoPoint(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
