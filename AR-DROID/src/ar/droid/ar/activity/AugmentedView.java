package ar.droid.ar.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import ar.droid.ar.common.ARData;
import ar.droid.ar.view.Marker;
import ar.droid.ar.view.Radar;

public class AugmentedView extends View {
    private static Radar radar = null;

    public AugmentedView(Context context) {
        super(context);
        
        // Crear Radar
        if (radar == null) radar = new Radar();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // Dibujar los marcadores RA!
	    for (int i=0; i<ARData.getMarkerCount(); i++) {
	        Marker marker = ARData.getMarker(i);
	        marker.draw(canvas);
	    }
        
        // Dibujar Radar y puntos
        radar.draw(canvas);
    }
}
