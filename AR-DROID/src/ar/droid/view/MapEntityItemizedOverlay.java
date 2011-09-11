package ar.droid.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import ar.droid.activity.EntityTabWidget;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MapEntityItemizedOverlay extends BalloonItemizedOverlay<EntityOverlayItem> {

	private List<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private MapView mapView;
	private Activity activity;
	
	
	public MapEntityItemizedOverlay(Drawable defaultMarker,MapView mapView,Activity activity) {
		super(boundCenterBottom(defaultMarker),mapView);
		mContext = mapView.getContext();
		this.mapView = mapView;
		this.activity = activity;
		populate();
	}

	@Override
	protected EntityOverlayItem createItem(int i) {
		 return (EntityOverlayItem)mOverlays.get(i);
	}
	
	public void addOverlay(EntityOverlayItem overlay) {
		mOverlays.add(overlay);
	    populate();
	}

	@Override
	public int size() {
		return mOverlays.size();

	}
	
	public List<OverlayItem> getOverlas() {
		return mOverlays;
	}

	@Override
	protected boolean onBalloonTap(int index, EntityOverlayItem item) {
		//Toast.makeText(mContext,item.getEntity().getUrl(),Toast.LENGTH_LONG).show();
		//aca hay que disparar la vista de la actividad con los datos de la entidad
		//tengo en el item la entidad seleccionada	
		//ahora cuando se selecciona en la ventanita muestra la url de la entidad
		// no se puede psar en el intent tipo Objets asi que por ahora
		//paso estos datso, pero se tiene que poder acceder de la vista entidad a la entidad selccionada
		//para recuperar toda la info.
		
		//ver si lo pongo parcelable o lo dejo cmo esta ahora¿¿
		
		 hideBalloon();
		 Intent i = new Intent(activity.getApplicationContext(), EntityTabWidget.class);
		 i.putExtra("idEntity", item.getEntity().getId());        
         activity.startActivityForResult(i,0);	 	 
         return true;
	}

	

}
