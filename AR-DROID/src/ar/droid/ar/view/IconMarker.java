package ar.droid.ar.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import ar.droid.ar.view.object.PaintableIcon;
import ar.droid.ar.view.object.PaintablePosition;

public class IconMarker extends Marker {
    private Bitmap bitmap = null;

    public IconMarker(String name, double latitude, double longitude, double altitude, int color, Bitmap bitmap) {
        super(name, latitude, longitude, altitude, color);
        this.bitmap = bitmap;
    }
    
    @Override
    public void drawIcon(Canvas canvas) {
    	if(bitmap==null) return;
    	
        float maxHeight = Math.round(canvas.getHeight() / 10f) + 1;
        
        PaintableIcon icon = new PaintableIcon(bitmap);
        PaintablePosition iconContainter = new PaintablePosition(icon, (circleVector.x - maxHeight/1.5f), (circleVector.y - maxHeight/1.5f), 0, 2);
        iconContainter.paint(canvas);
    }
}
