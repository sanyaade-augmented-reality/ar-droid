package ar.droid.ar.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import ar.droid.ar.common.ARData;
import ar.droid.ar.view.Marker;
import ar.droid.ar.view.Radar;
import ar.droid.config.AppPreferences;

public class AugmentedView extends View {
	private static Radar radar = null;

	public AugmentedView(Context context) {
		super(context);
        
        // Crear Radar
        if (radar == null)
        	radar = new Radar();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		// Dibujar los marcadores RA!
	    for (Marker marker : ARData.getMarkers()) {
	        marker.draw(canvas);
	    }

		// Dibujar circulo y marcas en el
		if(AppPreferences.getBool("raRadarPref", true)){
			radar.draw(canvas);
		}
	}
}
