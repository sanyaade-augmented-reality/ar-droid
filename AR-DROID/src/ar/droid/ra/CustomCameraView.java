package ar.droid.ra;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CustomCameraView extends SurfaceView  {

	private Camera camera;
    private SurfaceHolder previewHolder;

	public CustomCameraView(Context context) {
		super(context);
		previewHolder = this.getHolder();
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        previewHolder.addCallback(new SurfaceHolderListener(camera, previewHolder));
	}
}
