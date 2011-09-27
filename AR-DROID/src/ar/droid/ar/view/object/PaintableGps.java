package ar.droid.ar.view.object;

import android.graphics.Canvas;

/**
 * This class extends PaintableObject to draw a circle with a given radius and a stroke width.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class PaintableGps extends PaintableObject {
    private float radius = 0;
    private float strokeWidth = 0;
    private boolean fill = false;
    private int color = 0;
    
    public PaintableGps(float radius, float strokeWidth, boolean fill, int color) {
    	set(radius, strokeWidth, fill, color);
    }
    
    public void set(float radius, float strokeWidth, boolean fill, int color) {
        this.radius = radius;
        this.strokeWidth = strokeWidth;
        this.fill = fill;
        this.color = color;
    }

    @Override
    public void paint(Canvas canvas) {
    	if (canvas==null) return;
    	
        setStrokeWidth(strokeWidth);
        setFill(fill);
        setColor(color);
        paintCircle(canvas, 0, 0, radius);
    }

    @Override
    public float getWidth() {
        return radius;
    }

    @Override
    public float getHeight() {
        return radius;
    }
}
