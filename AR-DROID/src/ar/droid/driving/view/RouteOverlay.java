package ar.droid.driving.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class RouteOverlay extends Overlay {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private GeoPoint gp1;
	private GeoPoint gp2;
	private int color;
	
	public RouteOverlay(Drawable defaultMarker,Context context,GeoPoint gp1,GeoPoint gp2,int color) {
		//super(boundCenterBottom(defaultMarker));
		mContext = context;
		this.gp1 = gp1;
		this.gp2 = gp2;
		this.color= color;
	}


	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,	long when) {
		 Projection projection = mapView.getProjection();  
		 Paint paint = new Paint();  
		 Point point = new Point();  
		 projection.toPixels(gp1, point);  
		 paint.setColor(color);  
		 Point point2 = new Point();  
		 projection.toPixels(gp2, point2);  
		 paint.setStrokeWidth(5);  
		 paint.setAlpha(120);  
		 canvas.drawLine(point.x, point.y, point2.x, point2.y, paint); 
		return super.draw(canvas, mapView, shadow, when);
	}
	


	

}
