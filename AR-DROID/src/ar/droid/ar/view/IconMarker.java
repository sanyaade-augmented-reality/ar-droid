package ar.droid.ar.view;

import ar.droid.ar.view.object.PaintableIcon;
import ar.droid.ar.view.object.PaintablePosition;
import ar.droid.model.Entity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class IconMarker extends Marker {
    private Bitmap bitmap = null;
    private PaintableIcon icon = null;
    private PaintablePosition iconContainter = null;

    public IconMarker(Entity entity, Drawable drawable) {
        super(entity);
        this.bitmap = ((BitmapDrawable) drawable).getBitmap();
        icon = new PaintableIcon(bitmap);
    }
    
    @Override
    public void drawIcon(Canvas canvas) {
    	if (bitmap==null) return;
    	
        float maxHeight = Math.round(canvas.getHeight() / 10f) + 1;
        
        if (iconContainter==null) 
        	iconContainter = new PaintablePosition(icon, (circleVector.x - maxHeight/1.5f), (circleVector.y - maxHeight/1.5f), 0, 2);
        else 
        	iconContainter.set(icon, (circleVector.x - maxHeight/1.5f), (circleVector.y - maxHeight/1.5f), 0, 2);
        
        iconContainter.paint(canvas);
    }
}
