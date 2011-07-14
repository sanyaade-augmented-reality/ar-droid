package ar.droid.view;

import ar.droid.model.Event;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class EventOverlayItem extends OverlayItem {

	private Event event;
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public EventOverlayItem(GeoPoint point, String title, String snippet,Event event) {
		super(point, title, snippet);
		setEvent(event);
	}

}
