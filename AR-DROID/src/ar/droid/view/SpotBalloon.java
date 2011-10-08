package ar.droid.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import ar.droid.config.AppPreferences;

public class SpotBalloon extends Drawable {

	private Paint spotBalloonPaint = new Paint();
	private static final Paint blackTextPaint = new Paint();
	private static final int HEIGHT = 24;
	private static final int WIDTH = 30;
	
	private float multipler = AppPreferences.getInt("iconSizePref", 2);

	public SpotBalloon(int color, boolean multiply) {
		spotBalloonPaint.setColor(color);
		spotBalloonPaint.setAntiAlias(true);
		blackTextPaint.setARGB(255, 0, 0, 0);
		blackTextPaint.setAntiAlias(true);
		if(!multiply)
			multipler = 2;
		else{
			if(multipler == 1){
				multipler = (float) 0.75;
			}
			else if(multipler == 2){
				multipler = (float) 1.25;
			}
			else if(multipler == 3){
				multipler = (float) 1.75;
			}	
		}
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.setViewport(WIDTH, HEIGHT);
		canvas.drawCircle(0, 0, 10 * multipler, blackTextPaint);
		canvas.drawCircle(0, 0, 10 * multipler, spotBalloonPaint);

	}

	@Override
	public int getOpacity() {
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
	}
}
