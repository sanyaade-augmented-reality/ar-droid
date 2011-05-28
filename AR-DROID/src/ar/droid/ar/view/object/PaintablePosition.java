package ar.droid.ar.view.object;

import android.graphics.Canvas;

public class PaintablePosition extends PaintableObject {
    private float myX=0, myY=0, width=0, height=0;
    private PaintableObject obj = null;
    private float objX=0, objY=0, objRotation=0, objScale=0;

    public PaintablePosition(PaintableObject drawObj, float x, float y, float rotation, float scale) {
        obj = drawObj;
        objX = x;
        objY = y;
        objRotation = rotation;
        objScale = scale;
        float w = obj.getWidth();
        float h = obj.getHeight();

        myX = w / 2;
        myY = 0;

        width = w * 2;
        height = h * 2;
    }
    
    public void move(float x, float y) {
        objX = x;
        objY = y;
    }

    public void paint(Canvas canvas) {
        paintObj(canvas, obj, objX, objY, objRotation, objScale);
    }
    
    public float getX() {
        return myX;
    }
    
    public float getY() {
        return myY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
