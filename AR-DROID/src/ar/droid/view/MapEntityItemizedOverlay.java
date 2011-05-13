package ar.droid.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.MapView;

public class MapEntityItemizedOverlay extends BalloonItemizedOverlay<EntityOverlayItem> {

	private ArrayList<EntityOverlayItem> mOverlays = new ArrayList<EntityOverlayItem>();
	private Context mContext;
	private MapView mapView;
	
	public MapEntityItemizedOverlay(Drawable defaultMarker,MapView mapView) {
		super(boundCenterBottom(defaultMarker),mapView);
		mContext = mapView.getContext();
		this.mapView = mapView;
	}

	@Override
	protected EntityOverlayItem createItem(int i) {
		 return mOverlays.get(i);
	}
	
	public void addOverlay(EntityOverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	public int size() {
		return mOverlays.size();

	}
	

	@Override
	protected boolean onBalloonTap(int index, EntityOverlayItem item) {
		Toast.makeText(mContext,item.getEntity().getUrl(),Toast.LENGTH_LONG).show();
		//aca hay que disparar la vista de la actividad con los datos de la entidad
		//tengo en el item la entidad seleccionada	
		//ahora cuando se selecciona en la ventanita muestra la url de la entidad
		return true;
	}


}
