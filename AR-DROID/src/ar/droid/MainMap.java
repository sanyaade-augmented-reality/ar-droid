package ar.droid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import ar.droid.admin.reader.view.TypeEntityAdapter;
import ar.droid.admin.reader.view.TypeEventsAdapter;
import ar.droid.ar.common.ARData;
import ar.droid.config.AppPreferences;
import ar.droid.driving.DrivingDirectionsFactory;
import ar.droid.driving.IDirectionsListener;
import ar.droid.driving.Leg;
import ar.droid.driving.Mode;
import ar.droid.driving.Route;
import ar.droid.driving.Step;
import ar.droid.driving.view.RouteOverlay;
import ar.droid.location.LocationListenerGPS;
import ar.droid.location.MyCustomLocationOverlay;
import ar.droid.location.MyLocationOverlayFirstRun;
import ar.droid.model.Entity;
import ar.droid.model.Event;
import ar.droid.model.Resource;
import ar.droid.model.TypeEntity;
import ar.droid.model.TypeEvent;
import ar.droid.resources.ImageHelperFactory;
import ar.droid.sound.SoundManager;
import ar.droid.view.EntityOverlayItem;
import ar.droid.view.EventOverlayItem;
import ar.droid.view.MapEntityItemizedOverlay;
import ar.droid.view.MapEventItemizedOverlay;
import ar.droid.view.SpotBalloon;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MainMap extends MapActivity implements IDirectionsListener{
	static String TAG = MainMap.class.getName();
	static int RESULT_BACK = 10101;
	
	// splash screen
	protected boolean _splashActive = true;
	protected int _splashTime = 2000;
	
	private MapView mapView;
    private MyLocationOverlay myLocationOverlay;
	//private Resources resources;
	private LocationManager locationManager;
	private boolean showEvents=false;
		
	private ProgressDialog progressDialog = null;
	
	private List<Event> allevents = new ArrayList<Event>();
	
	private boolean goToLocation = true;

	private boolean isRouteDisplayed = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
        // crear location manager
        if(locationManager == null)
        	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        // pantalla completa
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // setear layout
        setContentView(R.layout.mainmap);
        
        // crear mapa
        mapView = (MapView) findViewById(R.id.mapview);
        // resources = getResources();
        mapView.displayZoomControls(true);
        mapView.setBuiltInZoomControls(true);
    	mapView.getController().setZoom(17);
    	
    	//carga entidades al iniciar el mapa
    	showEntities(true);       
       
    	// si el GPS no esta habilitado informar
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
        	Log.d(TAG, "GPS deshabilitado");
        	this.showMsgGPSDisabled();
        }
        
        Location location;
     	// crear lisener para el GPS
    	int minTime = AppPreferences.getInt("gpsTimePref", 5000);
        int minDistance = AppPreferences.getInt("gpsDistPref", 5);
        
    	// salir si ya esta creada la instancia
    	if(savedInstanceState != null){
    		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, new LocationListenerGPS(getApplicationContext(), this, location));
        	return;
    	}
        
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, new LocationListenerGPS(getApplicationContext(), this, location));

    }
    
    @Override
    public boolean onSearchRequested() {
         startSearch(null, false, null, false);
         return true;
     }
    
	private synchronized void initMap(){
    	Log.d(TAG, "Inicializando localización");
        
    	if(AppPreferences.getBool("posMapPref", true))
    		myLocationOverlay = new MyCustomLocationOverlay(this, mapView);
    	else
    		myLocationOverlay = new MyLocationOverlay(this, mapView);
    	
        mapView.getOverlays().add(myLocationOverlay);
        myLocationOverlay.enableMyLocation();
        if(AppPreferences.getBool("oriMapPref", true))
        	myLocationOverlay.enableCompass();
        
        if(!goToLocation)
        	return;
        
        myLocationOverlay.runOnFirstFix(new MyLocationOverlayFirstRun(mapView, myLocationOverlay));
        goToLocation = true;
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
    	
    	SoundManager.playSound(R.raw.message);
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
		
		return isRouteDisplayed;
	}
	
	@Override
	protected void onPause() {
	    super.onPause();
	    myLocationOverlay.disableMyLocation();
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	protected void onResume() {
		super.onResume();
	    myLocationOverlay.enableMyLocation();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 	 
	    //si viene de la pantalla de ver entidad limpiar mapa y armar la ruta   
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_map, menu);
	    return true;
	}
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
		SoundManager.playSound(R.raw.action);
		menu.clear();
		MenuInflater inflater = getMenuInflater();
		if (showEvents){
			if(this.isRouteDisplayed())
				 inflater.inflate(R.menu.menu_type_events_route, menu);
			else	
				inflater.inflate(R.menu.menu_type_events, menu);
		}
		else{ 
			if(this.isRouteDisplayed())
				 inflater.inflate(R.menu.menu_map_route, menu);
			else	
				inflater.inflate(R.menu.menu_map, menu);
		}
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SoundManager.playSound(R.raw.button);
	    // Handle item selection
	    switch (item.getItemId()) {
		    case R.id.menu_close:
		    	Log.d(TAG, "Salir");
				System.runFinalizersOnExit(true);
				System.exit(0);
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
		    		Runnable arrun = new Runnable() {
						
						@Override
						public void run() {
							ARData.setCurrentLocation(myLocationOverlay.getLastFix());
				    		Intent i = new Intent(getApplicationContext(), MainAR.class);
				    		startActivityForResult(i, 0);
						}
					};
		    		Thread thread = new Thread(null, arrun, "RA");
		    		runOnUiThread(thread);
		    		
			    	return true;
		    	}
		    	catch (Exception e) {
		    		this.showMsgCameraError();
		    		Log.d(TAG, "Error inicializando Camara", e);
			    	return true;
				}
		    case R.id.menu_config:
		    	Intent myIntent = new Intent(MainMap.this, Config.class);
	    		startActivity(myIntent);
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
		        
		    case R.id.menu_events:
		    	showOptionsEvents();
		        return true;
		    case R.id.menu_entities:
		    	showEntities(false);
		    	showOptionsTypeEntities();
		        return true;		    	    	
		    case R.id.menu_show_type_events:
		    	showOptionsTypeEvents();
		    	return true;
		    case R.id.borrar_ruta:
		    	this.removeRoute();
		        return true;	    	    	
		    case R.id.menu_events_volver:
		    	showEntities(false);
		    	return true;	
		    default:
		        return super.onOptionsItemSelected(item);
	    }
	}
		
	private void removeRoute() {
		isRouteDisplayed = false;
		if(showEvents)
			showEvents(allevents);
		else
			this.showEntities(false);
	}

	private void showOptionsTypeEntities() {
		//showEntities=true;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Capas - Entidades ");
		List<TypeEntity> typeEntity = Resource.getInstance().getTypeEntities();
		final ArrayAdapter<TypeEntity> itemlist = new TypeEntityAdapter(this,typeEntity);
		builder.setSingleChoiceItems(itemlist,-1,new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, final int which) {
				TypeEntity typeEntity = itemlist.getItem(which);
				dialog.dismiss();
				List<Entity> entityFilter = new ArrayList<Entity>();
				for (Entity entity: Resource.getInstance().getEntities()) {
					if (entity.getTypeEntity().equals(typeEntity)){
						entityFilter.add(entity);
					}
				}
				showEntities(entityFilter);
			}
		});
		
		SoundManager.playSound(R.raw.message);
		AlertDialog alert = builder.create();
		alert.getListView().setBackgroundColor(Color.WHITE);
		alert.show();
		
		
	}

	private void showOptionsTypeEvents() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Capas - Eventos ");
		List<TypeEvent> typeEvents = Resource.getInstance().getTypeEvents();
		final ArrayAdapter<TypeEvent> itemlist = new TypeEventsAdapter(this,typeEvents);
		builder.setSingleChoiceItems(itemlist,-1,new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, final int which) {
				TypeEvent typeEvent = itemlist.getItem(which);
				dialog.dismiss();	
				List<Event> eventsFilter = new ArrayList<Event>();
				for (Event event: allevents) {
					if (event.getTypeEvent().equals(typeEvent)){
						eventsFilter.add(event);
					}
				}
				showEvents(eventsFilter);
			}
		});
		
		SoundManager.playSound(R.raw.message);
		AlertDialog alert = builder.create();
		alert.getListView().setBackgroundColor(Color.WHITE);
		alert.show();
		
	}

	/**muestra en el mapa todo los eventos registrado en el sistema
	 * */
	private void showEntities(boolean gotoLoc){
    	showEvents = false;
		//Se recupera las entidades a mostrar en el mapa
		List<Entity> entities = Resource.getInstance().getEntities();
		
		goToLocation = gotoLoc;
		showEntities(entities);
	}
	
	private void showEntities(final List<Entity> entities){
		// inicializar mapa
		mapView.getOverlays().clear();
        initMap();
		
        Runnable thread = new Runnable() {
			
			@Override
			public void run() {
			
	            	//se agrupan entidades por tipo de entidad
	        		Iterator<Entity> itEnt = entities.iterator();
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
	        			Drawable iconTypeEntity = ImageHelperFactory.createImageHelper().getIconTypeEntity(typeEntity);
	        			iconTypeEntity = scaleImage(iconTypeEntity);
	        			iconTypeEntity.setBounds(0, 0, iconTypeEntity.getIntrinsicWidth(), iconTypeEntity.getIntrinsicHeight());
	        		
	        			//Se crea el MapEntityOverlay que tendra todos las entidades para el mismo tipo
	        			MapEntityItemizedOverlay mapEntityOverlay = new MapEntityItemizedOverlay(iconTypeEntity,mapView,MainMap.this);
	        			while (itEnt.hasNext()) {
	        				Entity entity = (Entity) itEnt.next();
	        				
	        				// cortar descripción
	        				String description = entity.getDescription();
	        				if(description.length() > 200)
	        					description = description.substring(0, 200) + "...";
	        				
	        				EntityOverlayItem overlayitemEntity = new EntityOverlayItem(entity.getGeoPoint(),entity.getName(),description,entity);
	        				mapEntityOverlay.addOverlay(overlayitemEntity);
	        				
	        			}
	        			//se incorpora los overlay al mapa
	        			mapView.getOverlays().add(mapEntityOverlay);
	        			
	        		}
	        		mapView.postInvalidate();
			}
		}; 
		
		runOnUiThread(thread);
	}
	
	/**
	 * Redimensionar un BitmapDrawable
	 * @param drawable drawable a escalar
	 * @param scale escala (1 = 100%)
	 * @return
	 */
	private Drawable scaleImage(Drawable drawable) {
		float scale = AppPreferences.getInt("iconSizePref", 2);
		
		BitmapDrawable bdImage = (BitmapDrawable) drawable;
		Bitmap bitmapOrig = bdImage.getBitmap();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrig, 0, 0, bitmapOrig.getWidth(), bitmapOrig.getHeight(), matrix, true);
        BitmapDrawable bitmapDrawableResized = new BitmapDrawable(resizedBitmap);
        return bitmapDrawableResized; 
        
	}
	
	private void showOptionsEvents() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Filtro Eventos");
		String options[] = new String[]{"Hoy","7 Días","30 Días"};
		final String parameter[] = new String[]{"today","week","month"};
		
		builder.setItems(options,new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, final int which) {
				
        		showEvents(parameter[which]);
        						
			}
		});
		builder.setCancelable(true);
		
		SoundManager.playSound(R.raw.message);
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**muestra en el mapa los eventos de todas las entidades
	 * */
	private void showEvents(String option){
		showEvents=true;
		List<Event> ls =Resource.getInstance().getEvents(option);
		allevents =  ls;
		showEvents(ls);
	}	
	
	private void showEvents(final List<Event> eventsToShow){
		mapView.getOverlays().clear();
		
		goToLocation = false;
		initMap();

		// copy references to final
		final MapView mv = mapView;
		final Activity ac = this;
		
        Runnable thread = new Runnable() {
			
			@Override
			public void run() {
				Map<ar.droid.location.GeoPoint,List<Event>> posiciones = new HashMap<ar.droid.location.GeoPoint,List<Event>>();
				Iterator<Event> itEvent = eventsToShow.iterator();
				while (itEvent.hasNext()) {
					Event event = (Event) itEvent.next();
					List<Event> listEvents = new ArrayList<Event>();
					if (posiciones.get(event.getGeoPoint()) != null){
						listEvents = posiciones.get(event.getGeoPoint());
					}
					listEvents.add(event);
					
					posiciones.put(event.getGeoPoint(),listEvents);
				}
				
				Iterator <ar.droid.location.GeoPoint> itPoint = posiciones.keySet().iterator();
				while (itPoint.hasNext()) {
					ar.droid.location.GeoPoint geopoint = (ar.droid.location.GeoPoint) itPoint.next();
					List<Event> events = posiciones.get(geopoint);
					
					//aca cuando hay muchos eventos mostrar distinto en vez de circulo un cuadrado o circulo multicolor
					Drawable drawable = new SpotBalloon(Color.parseColor("#"+events.get(0).getTypeEvent().getColor()), true);
					if (events.size()>1)
						drawable = getResources().getDrawable(R.drawable.multievent);
					MapEventItemizedOverlay mapEventOverlay = new MapEventItemizedOverlay(drawable, mv, ac);

					itEvent = events.iterator();
					while (itEvent.hasNext()) {
						Event event = (Event) itEvent.next();			
						EventOverlayItem overlayitemEvent = new EventOverlayItem(event.getGeoPoint(),event.getTitle(),event.getDescription(),event);
						mapEventOverlay.addOverlay(overlayitemEvent);
						mapView.getOverlays().add(mapEventOverlay);
					}	
				}
				mapView.postInvalidate();
			}
		}; 
		
		runOnUiThread(thread);
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
    	
    	SoundManager.playSound(R.raw.message);
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
	protected void onActivityResult(int requestCode, int resultCode, final Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		
		//limpiar mapa los overlays?? o no???
		if (resultCode == RESULT_OK) {
			 //recupero la entidad
			 //se cargan las noticias en un thread
	        	Runnable viewRoute = new Runnable(){
	            public void run() {
	            	Mode mode = ar.droid.driving.Mode.WALKING;
	            	if(AppPreferences.getInt("routeType", 1) == 2)
	            		mode = ar.droid.driving.Mode.DRIVING;
	            	try{
	            		if(showEvents){
	            			Event event = Resource.getInstance().getEntity(intent.getExtras().getLong("idEntity")).getEvent(intent.getExtras().getLong("idEvent"));
	            			DrivingDirectionsFactory.createDrivingDirections().driveTo(myLocationOverlay.getMyLocation(), (GeoPoint) event.getGeoPoint(), mode, MainMap.this);
	            		}
	            		else{
	            			Entity entity = Resource.getInstance().getEntity(intent.getExtras().getLong("idEntity"));
	            			DrivingDirectionsFactory.createDrivingDirections().driveTo(myLocationOverlay.getMyLocation(), (GeoPoint) entity.getGeoPoint(), mode, MainMap.this);
	            		}
	    			}
	    			catch (Throwable t) {
	    				Log.e(TAG, "Error en recorrido", t);
	    				Toast.makeText(getApplicationContext(), "Ocurrió un error al determinar el recorrido hacia el destino", Toast.LENGTH_LONG).show();
	    			}
	            	progressDialog.dismiss();
	            }
	        };
	        Thread thread =  new Thread(null, viewRoute, "Recorrido");
	        
	        progressDialog = ProgressDialog.show(this,"","Cargando recorrido....");
	        runOnUiThread(thread);
	        
			
	  }
		else if (resultCode == RESULT_BACK){
			System.runFinalizersOnExit(true);
			System.exit(0);
		}
	}

	@Override
	public void directionAvailable(Route route) {
		if(isRouteDisplayed)
			this.removeRoute();
		else
			isRouteDisplayed = true;
		
		int color;
		// determinar color
		int routeType = AppPreferences.getInt("routeType", 1);
		if(routeType == 1)
			color = Color.BLUE;
		else
			color = Color.GREEN;
			
		Iterator<Leg> xIterator = route.getLegs().iterator();
		while (xIterator.hasNext()) {
			Leg leg = xIterator.next();
			Iterator<Step> xItSteps = leg.getSteps().iterator();
			while (xItSteps.hasNext()) {
				Step step = xItSteps.next();
				for (int i = 1; i < step.getPolyline().getPolylines().size(); i++) {
					RouteOverlay routeOverlay = new RouteOverlay(null, mapView.getContext(), step.getPolyline().getPolylines().get(i-1),step.getPolyline().getPolylines().get(i), color);
					mapView.getOverlays().add(routeOverlay);			
				}
			}
		}			

		mapView.postInvalidate();
		progressDialog.dismiss();
	}

	@Override
	public void directionNotAvailable() {
		progressDialog.dismiss();
		Toast.makeText(getApplicationContext(), "No se encontró un recorrido hasta el destino", Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onStop() {
		super.onResume();
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
	}
	
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Salir de AR-Droid?").setCancelable(false)
				.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						System.runFinalizersOnExit(true);
						System.exit(0);
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		SoundManager.playSound(R.raw.message);
		AlertDialog alert = builder.create();
		alert.show();
	}
}