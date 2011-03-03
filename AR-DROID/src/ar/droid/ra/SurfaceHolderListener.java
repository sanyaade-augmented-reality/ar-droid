package ar.droid.ra;


import org.apache.http.RequestLine;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;

public class SurfaceHolderListener implements android.view.SurfaceHolder.Callback {

	private Camera camera;
	//private SurfaceHolder previewHolder;
	
	public SurfaceHolderListener(Camera camera,SurfaceHolder previewHolder) {
		this.camera = camera;
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		  Parameters params = camera.getParameters();
	      params.setPreviewSize(width, height);
	      params.setPictureFormat(PixelFormat.JPEG);
	     // previewHolder = holder;
	      camera.setParameters(params);
	      camera.startPreview();
	      
	}	

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		
		camera=android.hardware.Camera.open();
	    try {
	    	camera.setPreviewDisplay(holder);
        }  catch (Throwable t){ }

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		 camera.stopPreview();
	     camera.release();
	 }

	
}
