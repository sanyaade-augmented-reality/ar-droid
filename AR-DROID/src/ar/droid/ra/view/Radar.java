package ar.droid.ra.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import ar.droid.R;

public class Radar extends View {

	private Bitmap compass_appearance;
	private Bitmap compass_shadow;
	public Radar(Context context) {
		super(context);
		this.compass_appearance = BitmapFactory.decodeResource(getResources(),R.drawable.compass_simple);
		this.compass_shadow = BitmapFactory.decodeResource(getResources(),R.drawable.compass_simple_shadow);
	
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);	
		Log.i("algo","algo");
	  canvas.drawBitmap(compass_appearance, 0,0, null);
	  
	  Paint mPaint = new Paint();  
	  mPaint.setColor(Color.rgb(0x255, 0x0, 0x124));  
	  mPaint.setAntiAlias(true);
	   mPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)); 
	  canvas.drawText("Marisa", 150, 150,mPaint);
	  
	}

}
