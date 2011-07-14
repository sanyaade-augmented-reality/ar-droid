package ar.droid.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import ar.droid.activity.EventTabWidget;
import ar.droid.location.GeoPoint;
import ar.droid.model.Event;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MapEventItemizedOverlay extends BalloonItemizedOverlay<EventOverlayItem> {

	
	private List<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private MapView mapView;
	private Activity activity;
	
	
	public MapEventItemizedOverlay(Drawable defaultMarker,MapView mapView,Activity activity) {
		super(boundCenterBottom(defaultMarker),mapView);
		mContext = mapView.getContext();
		this.mapView = mapView;
		this.activity = activity;
		populate();
	}

	@Override
	protected EventOverlayItem createItem(int i) {
		 return (EventOverlayItem)mOverlays.get(i);
	}
	
	public void addOverlay(EventOverlayItem overlay) {
		mOverlays.add(overlay);
	    populate();
	}

	@Override
	public int size() {
		return mOverlays.size();

	}
	
	protected Boolean getItemTap() {
		//si hay mas de un overlay en la misma posicion geografica
		return mOverlays.size()>1;
	}

	
	public List<OverlayItem> getItemsToShow(int index) {
		//aca necesito mostar en el diaglo los eventos de la misma ubicacion
		OverlayItem selected = mOverlays.get(index);
		Iterator<OverlayItem> it = mOverlays.iterator();
		List<OverlayItem> items = new ArrayList<OverlayItem>();
		//items.add(selected);
		while (it.hasNext()) {
			OverlayItem overlayItem = (OverlayItem) it.next();
			if (((GeoPoint)selected.getPoint()).compareTo((GeoPoint)overlayItem.getPoint())==0){
				items.add(overlayItem);				
			}
		}		
		return items;
	}

	@Override
	public boolean onItemTap(DialogInterface arg0, int index) {
		//recupero el elemento el la posicion arg1
		Toast.makeText(mContext,((EventOverlayItem)getItemsToShow(index).get(index)).getEvent().getDescription(),Toast.LENGTH_LONG).show();		
		Event event = ((EventOverlayItem)getItemsToShow(index).get(index)).getEvent();
		startActivity(event);
		return true;
		
	}
	
	@Override
	protected boolean onBalloonTap(int index, EventOverlayItem item) {
		hideBalloon();
		Event event = ((EventOverlayItem)getItemsToShow(index).get(index)).getEvent();
		startActivity(event);
		return true;
	}
	
	private void startActivity(Event event){
		Bundle bundle = new Bundle();
		bundle.putLong("idEvent", event.getId());
		bundle.putLong("idEntity", event.getEntity().getId());
		Intent i = new Intent(activity.getApplicationContext(), EventTabWidget.class);
        i.putExtras(bundle);
        activity.startActivity(i);		
	}
}
