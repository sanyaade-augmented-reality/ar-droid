package ar.droid.model;

import ar.droid.location.GeoPoint;

public class Event {
	private Long id;
	private String title;
	private String description;
	private TypeEvent typeEvent;
	private GeoPoint geoPoint;
	private EventCalendar eventCalendar;
	
	
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
