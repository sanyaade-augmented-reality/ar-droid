package ar.droid.ar.common;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import android.location.Location;
import ar.droid.ar.view.Marker;
import ar.droid.config.AppPreferences;

public abstract class ARData {
	private static final Logger logger = Logger.getLogger(ARData.class.getSimpleName());
    private static final Map<Long,Marker> markerList = new ConcurrentHashMap<Long,Marker>();
	
    private static float radius = AppPreferences.getInt("raDistPref", 2000) / 1000;
    private static Location currentLocation = null;
    private static Matrix rotationMatrix = null;
    
    public static float getRadius() {
        return radius;
    }
    
    public static void setCurrentLocation(Location currentLocation) {
        ARData.currentLocation = currentLocation;
        onLocationChanged(currentLocation);
    }
    public static Location getCurrentLocation() {
        return currentLocation;
    }
    
    public static void setRotationMatrix(Matrix rotationMatrix) {
        ARData.rotationMatrix = rotationMatrix;
    }
    public static Matrix getRotationMatrix() {
        return rotationMatrix;
    }

    //DataHandler
    public static void addMarkers(List<Marker> markers) {
    	if (markers==null) return;
    	
    	logger.info("Marker before: "+markerList.size());
        for(Marker ma : markers) {
            if (!markerList.containsKey(ma)) {
            	ma.calcRelativePosition(ARData.getCurrentLocation());
            	markerList.put(ma.getEntity().getId(),ma);
            }
        }
        logger.info("Marker count: "+markerList.size());
    }
        
    public static void onLocationChanged(Location location) {
    	for(Marker ma: markerList.values()) {
            ma.calcRelativePosition(location);
        }
    }

    public static Collection<Marker> getMarkers() {
        return markerList.values();
    }
    
    public static Marker getMarker(int index) {
        Long key = (Long) markerList.keySet().toArray()[index];
        return markerList.get(key);
    }
    
	public static int getMarkerCount() {
		return markerList.size();
	}
}
