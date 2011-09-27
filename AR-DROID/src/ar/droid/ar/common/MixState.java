package ar.droid.ar.common;

public class MixState {
	private static final MixVector looking = new MixVector();
	
	public float bearing = 0;
	public float pitch = 0;

	public void calcPitchBearing(Matrix rotationM) {
		if (rotationM==null) return;
		
		looking.set(0, 0, 0);
	    rotationM.transpose();
		looking.set(1, 0, 0);
		looking.prod(rotationM);
		bearing = ((int) (MixUtils.getAngle(0, 0, looking.x, looking.z)  + 360 ) % 360);

		rotationM.transpose();
		looking.set(0, 1, 0);
		looking.prod(rotationM);
		pitch = -MixUtils.getAngle(0, 0, looking.y, looking.z);
	}
}
