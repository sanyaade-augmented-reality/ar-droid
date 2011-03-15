package ar.droid.ra;

import java.io.IOException;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

public class SurfaceHolderListener implements Callback {
	static String TAG = SurfaceHolderListener.class.getName();

	private Camera camera;
	private SurfaceHolder previewHolder;

	public SurfaceHolderListener(Camera camera, SurfaceHolder previewHolder) {
		this.camera = camera;
		this.previewHolder = previewHolder;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
		try {
			camera.setPreviewDisplay(previewHolder);
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "Error abriendo camara: " + e.getLocalizedMessage());
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Parameters params = camera.getParameters();
		params.setPictureFormat(PixelFormat.JPEG);
		params.setFocusMode(Parameters.FOCUS_MODE_AUTO);
		previewHolder = holder;
		previewHolder.setSizeFromLayout();
		camera.setParameters(params);
		camera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.stopPreview();
		camera.release();
		camera = null;
	}
}
