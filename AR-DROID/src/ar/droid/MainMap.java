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
import android.graphics.drawable.Drawable;
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
import ar.droid.config.ARDROIDProperties;
import ar.droid.location.LocationListenerGPS;
import ar.droid.location.MyLocationOverlayFirstRun;
import ar.droid.model.Entity;
import ar.droid.model.ResourceHelperFactory;
import ar.droid.model.TypeEntity;
import ar.droid.resources.ImageHelperFactory;
import ar.droid.view.EntityOverlayItem;
import ar.droid.view.MapEntityItemizedOverlay;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MainMap extends MapActivity {
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
    	
    	// si el GPS no esta habilitado informar
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
        	Log.d(TAG, "GPS deshabilitado");
        	this.showMsgGPSDisabled();
        }
        
    	// salir si ya esta creada la instancia
    	if(savedInstanceState != null)
        	return;
    	
    	//carga entidades al iniciar el mapa
    	showEntities();       
       
    	// crear lisener para el GPS
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, new LocationListenerGPS(getApplicationContext(), this));
        
     
       /// List<Entity> xLs = ResourceHelperFactory.createResourceHelper().getEntities();
        
      
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
		return false;
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
		    		Intent myIntent = new Intent(MainMap.this, MainAR.class);
		    		startActivity(myIntent);
		    	}
		    	catch (Exception e) {
		    		this.showMsgCameraError();
		    		Log.d(TAG, "Error inicializando Camara");
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
		List<Entity> xLs = ResourceHelperFactory.createResourceHelper().getEntities();
		
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
			Drawable iconTypeEntity =ImageHelperFactory.createImageHelper().getImage(typeEntity.getIconUrl());
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
}