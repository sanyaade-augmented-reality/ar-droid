package ar.droid.ar.view;

import android.graphics.Canvas;
import android.graphics.Color;
import ar.droid.ar.camara.CameraModel;
import ar.droid.ar.common.ARData;
import ar.droid.ar.common.MixState;
import ar.droid.ar.common.MixUtils;
import ar.droid.ar.view.object.PaintableCircle;
import ar.droid.ar.view.object.PaintableLine;
import ar.droid.ar.view.object.PaintablePosition;
import ar.droid.ar.view.object.PaintableRadarPoints;
import ar.droid.ar.view.object.PaintableText;

public class Radar {
    public static float RADIUS = 40;
    
    private static final int LINE_COLOR = Color.argb(150,0,0,220);
    private static final float PAD_X = 10;
    private static final float PAD_Y = 20;
    private static final int RADAR_COLOR = Color.argb(100, 0, 0, 200);
    private static final int TEXT_COLOR = Color.rgb(255,255,255);
    private static final int TEXT_SIZE = 12;

    private static ar.droid.ar.common.MixState state = null;
    private static ScreenLine leftRadarLine = null;
    private static ScreenLine rightRadarLine = null;
    private static PaintablePosition leftLineContainer = null;
    private static PaintablePosition rightLineContainer = null;
    private static PaintablePosition circleContainer = null;
    private static PaintableRadarPoints radarPoints = null;

    public Radar() {
        if (state == null) state = new MixState();
        if (leftRadarLine == null) leftRadarLine = new ScreenLine();
        if (rightRadarLine == null) rightRadarLine = new ScreenLine();
    }

    public void draw(Canvas canvas) {
        state.calcPitchBearing(ARData.getRotationMatrix());

        drawRadarCircle(canvas);
        drawRadarPoints(canvas);
        drawRadarLines(canvas);
        drawRadarText(canvas);
    }
    
    private void drawRadarCircle(Canvas canvas) {
        if (circleContainer==null) {
            PaintableCircle paintableCircle = new PaintableCircle(RADAR_COLOR,RADIUS,true);
            circleContainer = new PaintablePosition(paintableCircle,PAD_X+RADIUS,PAD_Y+RADIUS,0,1);
        }
        circleContainer.paint(canvas);
    }
    
    private void drawRadarPoints(Canvas canvas) {
        if (radarPoints==null) radarPoints = new PaintableRadarPoints();
        
        PaintablePosition pointsContainer = new PaintablePosition(  radarPoints, 
                                                                    PAD_X, 
                                                                    PAD_Y, 
                                                                    -state.bearing, 
                                                                    1);
        pointsContainer.paint(canvas);
    }
    
    private void drawRadarLines(Canvas canvas) {
        //Left line
        if (leftLineContainer==null) {
            leftRadarLine.set(0, -RADIUS);
            leftRadarLine.rotate(-CameraModel.DEFAULT_VIEW_ANGLE / 2);
            leftRadarLine.add(PAD_X+RADIUS, PAD_Y+RADIUS);

            float leftX = leftRadarLine.x-(PAD_X+RADIUS);
            float leftY = leftRadarLine.y-(PAD_Y+RADIUS);
            PaintableLine leftLine = new PaintableLine(LINE_COLOR, leftX, leftY);
            leftLineContainer = new PaintablePosition(  leftLine, 
                                                        PAD_X+RADIUS, 
                                                        PAD_Y+RADIUS, 
                                                        0, 
                                                        1);
        }
        leftLineContainer.paint(canvas);
        
        //Right line
        if (rightLineContainer==null) {
            rightRadarLine.set(0, -RADIUS);
            rightRadarLine.rotate(CameraModel.DEFAULT_VIEW_ANGLE / 2);
            rightRadarLine.add(PAD_X+RADIUS, PAD_Y+RADIUS);
            
            float rightX = rightRadarLine.x-(PAD_X+RADIUS);
            float rightY = rightRadarLine.y-(PAD_Y+RADIUS);
            PaintableLine rightLine = new PaintableLine(LINE_COLOR, rightX, rightY);
            rightLineContainer = new PaintablePosition( rightLine, 
                                                        PAD_X+RADIUS, 
                                                        PAD_Y+RADIUS, 
                                                        0, 
                                                        1);
        }
        rightLineContainer.paint(canvas);
    }

    private void drawRadarText(Canvas canvas) {
        //Direction text
        int range = (int) (state.bearing / (360f / 16f)); 
        String  dirTxt = "";
        if (range == 15 || range == 0) dirTxt = "N"; 
        else if (range == 1 || range == 2) dirTxt = "NE"; 
        else if (range == 3 || range == 4) dirTxt = "E"; 
        else if (range == 5 || range == 6) dirTxt = "SE";
        else if (range == 7 || range == 8) dirTxt= "S"; 
        else if (range == 9 || range == 10) dirTxt = "SW"; 
        else if (range == 11 || range == 12) dirTxt = "W"; 
        else if (range == 13 || range == 14) dirTxt = "NW";
        int bearing = (int) state.bearing; 
        radarText(  canvas, 
                    ""+bearing+((char)176)+" "+dirTxt, 
                    (PAD_X + RADIUS), 
                    (PAD_Y - 5), 
                    true
                 );
        
        //Zoom text
        radarText(  canvas, 
                    MixUtils.formatDist(ARData.getRadius() * 1000), 
                    (PAD_X + RADIUS), 
                    (PAD_Y + RADIUS*2 -10), 
                    false
                 );
    }
    
    private void radarText(Canvas canvas, String txt, float x, float y, boolean bg) {
        PaintableText paintableText = new PaintableText(txt,TEXT_COLOR,TEXT_SIZE,bg);
        PaintablePosition paintedContainer = new PaintablePosition(paintableText,x,y,0,1);
        paintedContainer.paint(canvas);
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
