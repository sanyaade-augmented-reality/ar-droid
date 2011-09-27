package ar.droid.ar.view;

import java.text.DecimalFormat;


import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import ar.droid.ar.camara.CameraModel;
import ar.droid.ar.common.ARData;
import ar.droid.ar.common.MixUtils;
import ar.droid.ar.common.MixVector;
import ar.droid.ar.view.object.PaintableBoxedText;
import ar.droid.ar.view.object.PaintableGps;
import ar.droid.ar.view.object.PaintablePosition;
import ar.droid.location.PhysicalLocation;
import ar.droid.model.Entity;

public class Marker implements Comparable<Marker> {
    private static final int MAX_OBJECTS = 50;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("@#");
        
    private static final MixVector originVector = new MixVector(0, 0, 0);
    private static final MixVector upVector = new MixVector(0, 1, 0);

    private static CameraModel cam = null;
    
    private final MixVector tmpa = new MixVector();
    private final MixVector tmpb = new MixVector();
    private final MixVector tmpc = new MixVector();

    private PaintableBoxedText textBlock = null;
    private PaintablePosition txtContainter = null;
    
    private PaintableGps gps = null;
    private PaintablePosition gpsContainter = null;
    
    //Unique identifier of Marker
    protected String name = null;
	//Marker's physical location
    protected PhysicalLocation physicalLocation = new PhysicalLocation();
	// distance from user to PhysicalLocation in meters
    protected double distance = 0.0;

	// Draw properties
    protected boolean isVisible = false;
    protected MixVector circleVector = new MixVector();
    protected MixVector signVector = new MixVector();
    protected MixVector locationVector = new MixVector();
    
    protected int color = Color.WHITE;
    
    private Entity entity;
	
	public Marker(Entity entity) {
		set(entity.getName(), entity.getGeoPoint().getLatitudeE6()/1E6, entity.getGeoPoint().getLongitudeE6()/1E6,entity.getGeoPoint().getAltitude(), Color.YELLOW, entity);
	}
	
	public void set(String name, double latitude, double longitude, double altitude, int color, Entity entity) {
		this.name = name;
		this.physicalLocation.set(latitude,longitude,altitude);
		this.color = color;
		isVisible = false;
		circleVector.set(0, 0, 0);
		signVector.set(0, 0, 0);
		locationVector.set(0, 0, 0);
		this.entity = entity;
	}
	
	public Entity getEntity(){
		return entity;
	}
	
	public String getName(){
		return name;
	}

	public double getLatitude() {
		return physicalLocation.getLatitude();
	}
	
	public double getLongitude() {
		return physicalLocation.getLongitude();
	}
	
	public double getAltitude() {
		return physicalLocation.getAltitude();
	}
	
	public MixVector getLocationVector() {
		return locationVector;
	}

    public double getDistance() {
        return distance;
    }

    public int getMaxObjects() {
        return MAX_OBJECTS;
    }
    
    public void setColor(int color) {
    	this.color = color;
    }
    
    public int getColor() {
    	return color;
    }
    
    @Override
    public int compareTo(Marker another) {
    	if (another==null) throw new NullPointerException();
    	
        return Double.compare(this.getDistance(), another.getDistance());
    }

    @Override
    public boolean equals (Object marker) {
    	if(marker==null || name==null) return false;
    	
        return name.equals(((Marker)marker).getName());
    }

    private void update(Canvas canvas, float addX, float addY) {
    	if (canvas==null) return;
    	
    	if (cam==null) cam = new CameraModel(canvas.getWidth(), canvas.getHeight(), true);
        cam.setViewAngle(CameraModel.DEFAULT_VIEW_ANGLE);
        cam.setTransform(ARData.getRotationMatrix());
        populateMatrices(originVector, cam, addX, addY);
        calcVisibility();
    }

	private void populateMatrices(MixVector originalPoint, CameraModel cam, float addX, float addY) {
		if (originalPoint==null || cam==null) return;
		
		// Temp properties
		tmpa.set(originalPoint.x, originalPoint.y, originalPoint.z);
		tmpc.set(upVector.x, upVector.y, upVector.z);
		tmpa.add(locationVector); //3 
		tmpc.add(locationVector); //3
		tmpa.sub(cam.getLco()); //4
		tmpc.sub(cam.getLco()); //4
		tmpa.prod(cam.getTransform()); //5
		tmpc.prod(cam.getTransform()); //5

		tmpb.set(0, 0, 0);
		cam.projectPoint(tmpa, tmpb, addX, addY); //6
		circleVector.set(tmpb.x, tmpb.y, tmpb.z); //7
		cam.projectPoint(tmpc, tmpb, addX, addY); //6
		signVector.set(tmpb.x, tmpb.y, tmpb.z); //7
	}

	private void calcVisibility() {
		isVisible = false;
		
		float range = ARData.getRadius() * 1000;
		float scale = range / Radar.RADIUS;
        float x = getLocationVector().x / scale;
        float y = getLocationVector().z / scale;
		if ( (circleVector.z < -1f) && ((x*x+y*y)<(Radar.RADIUS*Radar.RADIUS)) ) {
			isVisible = true;
		}
	}

    private void updateDistance(Location location) {
    	if (location==null) return;
    	
        float[] dist=new float[1];
        Location.distanceBetween(getLatitude(), getLongitude(), location.getLatitude(), location.getLongitude(), dist);
        distance = dist[0];
    }

	public void calcRelativePosition(Location location) {
		if (location==null) return;
		
	    updateDistance(location);
		// An elevation of 0.0 probably means that the elevation of the
		// POI is not known and should be set to the users GPS height
		if (physicalLocation.getAltitude()==0.0) physicalLocation.setAltitude(location.getAltitude());
		 
		// compute the relative position vector from user position to POI location
		PhysicalLocation.convLocToVec(location, physicalLocation, locationVector);
	}

	public void draw(Canvas canvas) {
		if (canvas==null) return;

		//Calculate the visibility of this Marker
	    update(canvas,0,0);
	    
	    //If not visible then do nothing
	    if (!isVisible) return;
	    
	    //Draw the Icon and Text
	    drawIcon(canvas);
	    drawText(canvas);
	}

    public void drawIcon(Canvas canvas) {
    	if (canvas==null) return;
    	
        float maxHeight = Math.round(canvas.getHeight() / 10f) + 1;
        if (gps==null) gps = new PaintableGps((maxHeight / 1.5f), (maxHeight / 10f), true, getColor());
        
        if (gpsContainter==null) gpsContainter = new PaintablePosition(gps, circleVector.x, circleVector.y, 0, 1);
        else gpsContainter.set(gps, circleVector.x, circleVector.y, 0, 1);
        gpsContainter.paint(canvas);
    }

	public void drawText(Canvas canvas) {
		if (canvas==null) return;
		
	    String textStr = null;
	    if (distance<1000.0) {
	        textStr = name + " ("+ DECIMAL_FORMAT.format(distance) + "m)";          
	    } else {
	        double d=distance/1000.0;
	        textStr = name + " (" + DECIMAL_FORMAT.format(d) + "km)";
	    }

	    float maxHeight = Math.round(canvas.getHeight() / 10f) + 1;
	    if (textBlock==null) textBlock = new PaintableBoxedText(textStr, Math.round(maxHeight / 2f) + 1, 300);
	    else textBlock.set(textStr, Math.round(maxHeight / 2f) + 1, 300);
	    float x = signVector.x - textBlock.getWidth() / 2 -10;
	    float y = signVector.y + maxHeight;
	    float currentAngle = MixUtils.getAngle(circleVector.x, circleVector.y, signVector.x, signVector.y);
	    float angle = currentAngle + 90;
	    if (txtContainter==null) txtContainter = new PaintablePosition(textBlock, x, y, angle, 1);
	    else txtContainter.set(textBlock, x, y, angle, 1);
	    txtContainter.paint(canvas);
	}

	public boolean clickedMe(float x, float y) {
		float currentAngle = MixUtils.getAngle(circleVector.x, circleVector.y,
				signVector.x, signVector.y);
		//if the marker is not active (i.e. not shown in AR view) we don't have to check it for clicks
		if (!isVisible)
			return false;
		
		ScreenLine sl = new ScreenLine();
		sl.x = x - signVector.x;
		sl.y = y - signVector.y;
		sl.rotate(Math.toRadians(-(currentAngle + 90)));
		sl.x += txtContainter.getX();
		sl.y += txtContainter.getY();

		float objX = txtContainter.getX() - txtContainter.getWidth() / 2;
		float objY = txtContainter.getY() - txtContainter.getHeight() / 2;
		float objW = txtContainter.getWidth();
		float objH = txtContainter.getHeight();

		if (sl.x > objX && sl.x < objX + objW && sl.y > objY
				&& sl.y < objY + objH) {
			return true;
		} else {
			return false;
		}
	}
}

class ScreenLine {
    public float x, y;

    public ScreenLine() {
        set(0, 0);
    }

    public ScreenLine(float x, float y) {
        set(x, y);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void rotate(double t) {
        float xp = (float) Math.cos(t) * x - (float) Math.sin(t) * y;
        float yp = (float) Math.sin(t) * x + (float) Math.cos(t) * y;

        x = xp;
        y = yp;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }
}
