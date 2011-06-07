package ar.droid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import ar.droid.ar.common.ARData;
import ar.droid.config.ARDROIDProperties;
import ar.droid.driving.DrivingDirectionsFactory;
import ar.droid.driving.IDirectionsListener;
import ar.droid.driving.Leg;
import ar.droid.driving.Route;
import ar.droid.driving.Step;
import ar.droid.driving.view.RouteOverlay;
import ar.droid.location.LocationListenerGPS;
import ar.droid.location.MyLocationOverlayFirstRun;
import ar.droid.model.Entity;
import ar.droid.model.Resource;
import ar.droid.model.TypeEntity;
import ar.droid.resources.ImageHelperFactory;
import ar.droid.view.EntityOverlayItem;
import ar.droid.view.MapEntityItemizedOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MainMap extends MapActivity implements IDirectionsListener{
	static String TAG = MainMap.class.getName();
	
	// splash screen
	protected boolean _splashActive = true;
	protected int _splashTime = 2000;
	
	private MapView mapView;
    private MyLocationOverlay myLocationOverlay;
	private Resources resources;
	private LocationManager locationManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // crear location manager
        if(locationManager == null)
        	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        // pantalla completa
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // setear layout
        setContentView(R.layout.mainmap);
        
        ARDROIDProperties.createProperties(getApplicationContext());
        
        // crear mapa
        mapView = (MapView) findViewById(R.id.mapview);
        resources = getResources();
        mapView.displayZoomControls(true);
        mapView.setBuiltInZoomControls(true);
    	mapView.getController().setZoom(17);
    	
    	// inicializar mapa
    	this.initMap();
    	
    	//carga entidades al iniciar el mapa
    	showEntities();       
       
    	// si el GPS no esta habilitado informar
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
        	Log.d(TAG, "GPS deshabilitado");
        	this.showMsgGPSDisabled();
        }
        
        Location location;
     	// crear lisener para el GPS
    	int minTime = ARDROIDProperties.getInstance().getIntProperty("ar.droid.gps.mintime");
        int minDistance = ARDROIDProperties.getInstance().getIntProperty("ar.droid.gps.mindistance");
        
    	// salir si ya esta creada la instancia
    	if(savedInstanceState != null){
    		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, new LocationListenerGPS(getApplicationContext(), this, location));
        	return;
    	}
        
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, new LocationListenerGPS(getApplicationContext(), this, location));
 
    }
    
	private void initMap(){
    	Log.d(TAG, "Inicializando localización");
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        mapView.getOverlays().add(myLocationOverlay);
        myLocationOverlay.enableCompass();
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new MyLocationOverlayFirstRun(mapView, myLocationOverlay));
    } 

	private void showMsgGPSDisabled() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("El GPS esta deshabilitado!")
    	       .setCancelable(false)
    	       .setPositiveButton("Habilitar", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	               launchGPSOptions();
    	           }
    	       })
    	       .setNegativeButton("No hacer nada", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                dialog.cancel();

    	                // mostrar notificación
    	                Toast.makeText(getApplicationContext(), "El GPS está deshabilitado", Toast.LENGTH_LONG).show();
    	                
    	                // ubucación no disponible
    	        		TextView textView = (TextView) findViewById(R.id.address);
    	        		textView.setText("Ubicación no disponible");
    	        		textView.invalidate();
    	           }
    	       });
    	AlertDialog alert = builder.create();  
    	alert.show();
	}
    
	/**
	 * Lanza la actividad de configuración de localización del sistema
	 */
    private void launchGPSOptions() {
    	Log.d(TAG, "Lanzando configuración de localización");
    	Intent gpsOptionsIntent = new Intent(  
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
        this.startActivity(gpsOptionsIntent);
    }  

	@Override
	protected boolean isRouteDisplayed() {
		return true;
	}
	
	@Override
	protected void onPause() {
	    super.onPause();
	    myLocationOverlay.disableMyLocation();
	}

	@Override
	protected void onResume() {
		super.onResume();
	    myLocationOverlay.enableMyLocation();
	    //si viene de la pantalla de ver entidad limpiar mapa y armar la ruta
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_map, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
		    case R.id.menu_close:
		    	Log.d(TAG, "Salir");
		    	int pid = android.os.Process.myPid(); 
		    	android.os.Process.killProcess(pid); 
		        return true;
		    case R.id.menu_ar:
		    	// puede iniciar RA?
		    	if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
		    		
		    		// mostrar notificación
	                Toast.makeText(getApplicationContext(), "El GPS está deshabilitado, no puede iniciar la Vista RA", Toast.LENGTH_LONG).show();
	                return true;
	    		}
		    	// abrir realidad aumentada
		    	try{ 		
		    		ARData.setCurrentLocation(myLocationOverlay.getLastFix());
		    		Intent myIntent = new Intent(MainMap.this, MainAR.class);
		    		startActivity(myIntent);
		    	}
		    	catch (Exception e) {
		    		this.showMsgCameraError();
		    		Log.d(TAG, "Error inicializando Camara", e);
				}
		        return true;
		    case R.id.menu_find:
		        return true;
		    case R.id.menu_position:
		    	// el GPS esta habilitado?
		    	if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
		    		
		    		// mostrar notificación
	                Toast.makeText(getApplicationContext(), "El GPS está deshabilitado, no se puede determinar su posición", Toast.LENGTH_LONG).show();
	                return true;
	    		}
		    	// ir a mi posición
		    	new MyLocationOverlayFirstRun(mapView, myLocationOverlay).run();
		        return true;
		        
		    case R.id.menu_show:
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
	    }
	}
	

	
	
	private void showEntities(){
		//Se recupera las entidades a mostrar en el mapa
		List<Entity> xLs =Resource.getInstance().getEntities();
		
		//se agrupan entidades por tipo de entidad
		Iterator<Entity> itEnt = xLs.iterator();
		Map<TypeEntity, List<Entity>> types = new HashMap<TypeEntity, List<Entity>>();		
		while (itEnt.hasNext()) {
			Entity entity = (Entity) itEnt.next();
			List<Entity> listEntities = new ArrayList<Entity>();
			if (types.get(entity.getTypeEntity())!=null){
				listEntities = types.get(entity.getTypeEntity());
			}
			listEntities.add(entity);
			types.put(entity.getTypeEntity(), listEntities);
		}
	
		//se crean los overlays con la info de las entidades
		Iterator<TypeEntity> itTypesEnt = types.keySet().iterator();
		while (itTypesEnt.hasNext()) {
			TypeEntity typeEntity = itTypesEnt.next();
			itEnt = types.get(typeEntity).iterator();
			
			//se recupera el icono a mostrar para el tipo de entidad
			Drawable iconTypeEntity =ImageHelperFactory.createImageHelper().getIconTypeEntity(typeEntity);
			iconTypeEntity.setBounds(0, 0, iconTypeEntity.getIntrinsicWidth(), iconTypeEntity.getIntrinsicHeight());
		
			//Se crea el MapEntityOverlay que tendra todos las entidades para el mismo tipo
			MapEntityItemizedOverlay mapEntityOverlay = new MapEntityItemizedOverlay(iconTypeEntity,mapView,this);
			while (itEnt.hasNext()) {
				Entity entity = (Entity) itEnt.next();			
				EntityOverlayItem overlayitemEntity = new EntityOverlayItem(entity.getGeoPoint(),entity.getName(),entity.getDescription(),entity);
				mapEntityOverlay.addOverlay(overlayitemEntity);
				
			}
			//se incorpora los overlay al mapa
			mapView.getOverlays().add(mapEntityOverlay);
		}
		mapView.postInvalidate(); 
	}
	
	private void showEvents(){
		
	}
	
	private void showMsgCameraError() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Ocurrió un error al inicializar la captura de video!")
    	       .setCancelable(false)
    	       .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   finish();
    	           }
    	       });
    	AlertDialog alert = builder.create();  
    	alert.show();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// esconder splash screen
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	    	_splashActive = false;
	    }
	    return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		//limpiar mapa los overlays?? o no???
		if (resultCode == RESULT_OK) {
			 //recupero la entidad
		    Entity entity = Resource.getInstance().getEntity(intent.getExtras().getLong("idEntity"));
		    DrivingDirectionsFactory.createDrivingDirections().driveTo(myLocationOverlay.getMyLocation(), (GeoPoint)entity.getGeoPoint(), ar.droid.driving.Mode.WALKING,this);
	  }

	}

	@Override
	public void directionAvailable(Route route) {
			Iterator<Leg> xIterator = route.getLegs().iterator();
			while (xIterator.hasNext()) {
				Leg leg = xIterator.next();
				Iterator<Step> xItSteps = leg.getSteps().iterator();
				while (xItSteps.hasNext()) {
					Step step = xItSteps.next();
					for (int i = 1; i < step.getPolyline().getPolylines().size(); i++) {
						RouteOverlay routeOverlay = new RouteOverlay(null, mapView.getContext(), step.getPolyline().getPolylines().get(i-1),step.getPolyline().getPolylines().get(i), Color.rgb(202, 101, 0));
						mapView.getOverlays().add(routeOverlay);	
					}
				}
			}				
		// redraw map
		mapView.postInvalidate();
		
	}

	@Override
	public void directionNotAvailable() {
		// TODO Auto-generated method stub
		
	}
}