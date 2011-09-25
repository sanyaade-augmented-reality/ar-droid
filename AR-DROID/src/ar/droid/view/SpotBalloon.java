package ar.droid.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import ar.droid.config.ARDroidPreferences;

public class SpotBalloon extends BitmapDrawable {

	private Paint spotBalloonPaint = new Paint();
	private static final Paint blackTextPaint = new Paint();
	private static final int HEIGHT = 20;
	private static final int WIDTH = 100;

	// private String name;

	public SpotBalloon(int alpha, int r, int g, int b) {
		spotBalloonPaint.setARGB(alpha, r, g, b);
		spotBalloonPaint.setAntiAlias(true);
		blackTextPaint.setARGB(255, 0, 0, 0);
		blackTextPaint.setAntiAlias(true);
	}

	public SpotBalloon(int color) {
		spotBalloonPaint.setColor(color);
		spotBalloonPaint.setAntiAlias(true);
		blackTextPaint.setARGB(255, 0, 0, 0);
		blackTextPaint.setAntiAlias(true);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.setViewport(WIDTH, HEIGHT);
		canvas.drawCircle(0, 0, 7 * ARDroidPreferences.getInt("iconSizePref", 2), blackTextPaint);
		canvas.drawCircle(0, 0, 7 * ARDroidPreferences.getInt("iconSizePref", 2), spotBalloonPaint);

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
