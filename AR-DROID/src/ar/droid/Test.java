package ar.droid;

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import ar.droid.driving.DrivingDirectionsFactory;
import ar.droid.driving.Leg;
import ar.droid.driving.RoutePath;
import ar.droid.driving.Step;
import ar.droid.driving.view.RouteOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Test extends MapActivity {
    /** Called when the activity is first created. */
	private MapView mapView;
	private Resources resources;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmap);
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        resources = getResources();
    	//mapView.getController().setZoom(17);
        
            
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, new LocationListenerGPS());
    }
    
   
    
    private class LocationListenerGPS implements android.location.LocationListener{
		@Override
		public void onLocationChanged(Location location) {
			GeoPoint position = new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude()*1E6));
			
			//mapView.getOverlays().clear();
	        List<Overlay> mapOverlays = mapView.getOverlays();
			Drawable drawable = resources.getDrawable(R.drawable.androidmarker);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

			TestOverlay itemizedoverlay = new TestOverlay(drawable,mapView.getContext());     
	 		OverlayItem overlayitem = new OverlayItem(position, "Hola, Mundo!", "Estoy Aqui!");
			
			itemizedoverlay.addOverlay(overlayitem);
			mapOverlays.add(itemizedoverlay);
			
			/*de prueba para probar conexion al servidor ar-droid*/
			Log.i("antes de conectar ","hola");
			
			//ResourceHelper resourceHelper = new ResourceHelper();
			//List entities = resourceHelper.getEntities();
			
			//GeoPoint position2 = new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude()+100*1E6));
			
			GeoPoint position2 = new GeoPoint((int)(new Double(-34.9159100)*1E6),(int)(new Double(-57.9550000)*1E6));
			Log.i("position2 ",position2.toString());
			RoutePath routePath =DrivingDirectionsFactory.createDrivingDirections().driveTo(position, position2, ar.droid.driving.Mode.WALKING);
			
			if (routePath.getRoute() != null){
				Iterator<Leg> xIterator = routePath.getRoute().getLegs().iterator();
				while (xIterator.hasNext()) {
					Leg leg = xIterator.next();
					Iterator<Step> xItSteps = leg.getSteps().iterator();
					while (xItSteps.hasNext()) {
						Step step = xItSteps.next();
						for (int i = 1; i < step.getPolyline().getPolylines().size(); i++) {
							RouteOverlay routeOverlay = new RouteOverlay(drawable, mapView.getContext(), step.getPolyline().getPolylines().get(i-1),step.getPolyline().getPolylines().get(i), 255);
							mapOverlays.add(routeOverlay);	
						}
						
					}
				}				
			}
			
			//Log.i("entidades ", entities.toString());
			//Log.i("entidades ", ""+entities.size());
			
			/*de prueba para probar conexion al servidor ar-droid*/
			
			
			// move to location
			mapView.getController().animateTo(position);
	 
			// redraw map
			mapView.postInvalidate();
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    	
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    
    
}