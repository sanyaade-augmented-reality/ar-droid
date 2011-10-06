package ar.droid.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import ar.droid.config.AppPreferences;

public class SpotBalloon extends BitmapDrawable {

	private Paint spotBalloonPaint = new Paint();
	private static final Paint blackTextPaint = new Paint();
	private static final int HEIGHT = 20;
	private static final int WIDTH = 100;
	
	private int multipler = AppPreferences.getInt("iconSizePref", 2);

	public SpotBalloon(int color, boolean multiply) {
		spotBalloonPaint.setColor(color);
		spotBalloonPaint.setAntiAlias(true);
		blackTextPaint.setARGB(255, 0, 0, 0);
		blackTextPaint.setAntiAlias(true);
		if(!multiply)
			multipler = 1;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.setViewport(WIDTH, HEIGHT);
		canvas.drawCircle(0, 0, 7 * multipler, blackTextPaint);
		canvas.drawCircle(0, 0, 7 * multipler, spotBalloonPaint);

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
