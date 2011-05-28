package ar.droid.ar.camara;

import ar.droid.ar.common.Matrix;
import ar.droid.ar.common.MixVector;

public class CameraModel {
	public static final float DEFAULT_VIEW_ANGLE = (float) Math.toRadians(45);
	public Matrix transform = new Matrix();
	public MixVector lco = new MixVector();
	
	private int width = 0; 
	private int height = 0;
	private float viewAngle = 0F;
	private float distance = 0F;

	public CameraModel(int width, int height) {
		this(width, height, true);
	}

	public CameraModel(int width, int height, boolean init) {
		this.width = width;
		this.height = height;

		transform.toIdentity();
		lco.set(0, 0, 0);
	}

	public void setViewAngle(float viewAngle) {
		this.viewAngle = viewAngle;
		this.distance = (this.width / 2) / (float) Math.tan(viewAngle / 2);
	}

	public void setViewAngle(int width, int height, float viewAngle) {
		this.viewAngle = viewAngle;
		this.distance = (width / 2) / (float) Math.tan(viewAngle / 2);
	}

	public void projectPoint(MixVector orgPoint, MixVector prjPoint, float addX, float addY) {
		prjPoint.x = distance * orgPoint.x / -orgPoint.z;
		prjPoint.y = distance * orgPoint.y / -orgPoint.z;
		prjPoint.z = orgPoint.z;
		prjPoint.x = prjPoint.x + addX + width / 2;
		prjPoint.y = -prjPoint.y + addY + height / 2;
	}
	
	@Override
	public String toString() {
		return "CAM(" + width + "," + height + "," + viewAngle + "," + distance + ")";
	}
}
