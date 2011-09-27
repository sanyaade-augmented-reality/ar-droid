package ar.droid.ar.view.object;

import android.graphics.Canvas;

/**
 * This class extends PaintableObject to draw a circle with a given radius.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class PaintableCircle extends PaintableObject {
    private int color = 0;
    private float radius = 0;
    private boolean fill = false;
    
    public PaintableCircle(int color, float radius, boolean fill) {
    	set(color, radius, fill);
    }
    
    public void set(int color, float radius, boolean fill) {
        this.color = color;
        this.radius = radius;
        this.fill = fill;
    }

    @Override
    public void paint(Canvas canvas) {
    	if (canvas==null) return;
    	
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
