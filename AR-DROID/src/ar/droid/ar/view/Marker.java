package ar.droid.ar.view;

import java.text.DecimalFormat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import ar.droid.ar.camara.CameraModel;
import ar.droid.ar.common.ARData;
import ar.droid.ar.common.MixUtils;
import ar.droid.ar.common.MixVector;
import ar.droid.ar.common.PhysicalLocation;
import ar.droid.ar.view.object.PaintableBoxedText;
import ar.droid.ar.view.object.PaintableGps;
import ar.droid.ar.view.object.PaintablePosition;
import ar.droid.model.Entity;

public class Marker implements Comparable<Marker> {
    private static final int MAX_OBJECTS = 100;
    
    // entidad a la que representa el marker
    Entity entity;
	
    //Marker's physical location
    protected PhysicalLocation physicalLocation = null;
	// distance from user to PhysicalLocation in meters
    protected double distance = 0.0;

	// Draw properties
    protected boolean isVisible = false;
    protected MixVector circleVector = new MixVector();
    protected MixVector signVector = new MixVector();
    protected MixVector locationVector = new MixVector();
    protected MixVector originVector = new MixVector(0, 0, 0);
    protected MixVector upVector = new MixVector(0, 1, 0);
    
    protected int color = Color.WHITE;
    protected PaintablePosition txtContainter;
	
	public Marker(Entity entity) {
		this.entity = entity;
		this.physicalLocation = new PhysicalLocation(entity.getGeoPoint().getLatitudeE6()/1E6, entity.getGeoPoint().getLongitudeE6()/1E6,entity.getGeoPoint().getAltitude());
	}
	
	public Entity getEntity(){
		return entity;
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
        return Double.compare(this.getDistance(), another.getDistance());
    }

    @Override
    public boolean equals (Object marker) {
        return this.getEntity().getId().equals(((Marker)marker).getEntity().getId());
    }

    private void update(Canvas canvas, float addX, float addY) {
        CameraModel cam = new CameraModel(canvas.getWidth(), canvas.getHeight(), true);
        cam.setViewAngle(CameraModel.DEFAULT_VIEW_ANGLE);
        cam.transform = ARData.getRotationMatrix();
        populateMatrices(originVector, cam, addX, addY);
        calcVisibility();
    }

	private void populateMatrices(MixVector originalPoint, CameraModel cam, float addX, float addY) {
		// Temp properties
		MixVector tmpa = new MixVector(originalPoint);
		MixVector tmpc = new MixVector(upVector);
		tmpa.add(locationVector); //3 
		tmpc.add(locationVector); //3
		tmpa.sub(cam.lco); //4
		tmpc.sub(cam.lco); //4
		tmpa.prod(cam.transform); //5
		tmpc.prod(cam.transform); //5

		MixVector tmpb = new MixVector();
		cam.projectPoint(tmpa, tmpb, addX, addY); //6
		circleVector.set(tmpb); //7
		cam.projectPoint(tmpc, tmpb, addX, addY); //6
		signVector.set(tmpb); //7
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
        float[] dist=new float[3];
        Location.distanceBetween(getLatitude(), getLongitude(), location.getLatitude(), location.getLongitude(), dist);
        distance = dist[0];
    }

	public void calcRelativePosition(Location location) {
	    updateDistance(location);
		// An elevation of 0.0 probably means that the elevation of the
		// POI is not known and should be set to the users GPS height
		if(physicalLocation.getAltitude()==0.0) physicalLocation.setAltitude(location.getAltitude());
		 
		// compute the relative position vector from user position to POI location
		PhysicalLocation.convLocToVec(location, physicalLocation, locationVector);
	}

	public void draw(Canvas canvas) {
	    update(canvas,0,0);	    
	    if (!isVisible)
	    	return;	    
	    drawIcon(canvas);
	    drawText(canvas);
	}

    public void drawIcon(Canvas canvas) {
        float maxHeight = Math.round(canvas.getHeight() / 10f) + 1;
        PaintableGps gps = new PaintableGps((maxHeight / 1.5f), (maxHeight / 10f), true, getColor());
        PaintablePosition gpsContainter = new PaintablePosition(gps, circleVector.x, circleVector.y, 0, 1);
        gpsContainter.paint(canvas);
    }

	public void drawText(Canvas canvas) {
	    String textStr="";
	    DecimalFormat df = new DecimalFormat("@#");
	    if (distance<1000.0) {
	        textStr = getEntity().getName() + " ("+ df.format(distance) + "m)";          
	    } else {
	        double d=distance/1000.0;
	        textStr = getEntity().getName() + " (" + df.format(d) + "km)";
	    }

	    float maxHeight = Math.round(canvas.getHeight() / 10f) + 1;
	    PaintableBoxedText textBlock = new PaintableBoxedText(textStr, Math.round(maxHeight / 2f) + 1, 300);
	    float x = signVector.x - textBlock.getWidth() / 2;
	    float y = signVector.y + maxHeight;
	    float currentAngle = MixUtils.getAngle(circleVector.x, circleVector.y, signVector.x, signVector.y);
	    float angle = currentAngle + 90;
	    txtContainter = new PaintablePosition(textBlock, x, y, angle, 1);
	    txtContainter.paint(canvas);
	}

	public boolean clickedMe(float x, float y) {
		float currentAngle = MixUtils.getAngle(circleVector.x, circleVector.y,
				signVector.x, signVector.y);
		//if the marker is not active (i.e. not shown in AR view) we don't have to check it for clicks
		if (!isVisible)
			return false;
		
		//TODO adapt the following to the variable radius!
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