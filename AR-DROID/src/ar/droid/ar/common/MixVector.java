package ar.droid.ar.common;

public class MixVector {
	public float x;
	public float y;
	public float z;

	public MixVector() {
		this(0, 0, 0);
	}

	public MixVector(MixVector v) {
		this(v.x, v.y, v.z);
	}

	public MixVector(float v[]) {
		this(v[0], v[1], v[2]);
	}

	public MixVector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(MixVector v) {
		if (v==null) return;
		
		set(v.x, v.y, v.z);
	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj==null) return false;

		MixVector v = (MixVector) obj;
		return (v.x == x && v.y == y && v.z == z);
	}

	public boolean equals(float x, float y, float z) {
		return (this.x == x && this.y == y && this.z == z);
	}

	@Override
	public String toString() {
		return "<" + x + ", " + y + ", " + z + ">";
	}

	public void add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public void add(MixVector v) {
		if (v==null) return;
		
		add(v.x, v.y, v.z);
	}

	public void sub(float x, float y, float z) {
		add(-x, -y, -z);
	}

	public void sub(MixVector v) {
		if (v==null) return;
		
		add(-v.x, -v.y, -v.z);
	}

	public void mult(float s) {
		x *= s;
		y *= s;
		z *= s;
	}

	public void divide(float s) {
		x /= s;
		y /= s;
		z /= s;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public float length2D() {
		return (float) Math.sqrt(x * x + z * z);
	}

	public void norm() {
		divide(length());
	}

	public float dot(MixVector v) {
		if (v==null) return 0f;

		return x * v.x + y * v.y + z * v.z;
	}

	public void cross(MixVector u, MixVector v) {
		if (v==null || u==null) return;
		
		float x = u.y * v.z - u.z * v.y;
		float y = u.z * v.x - u.x * v.z;
		float z = u.x * v.y - u.y * v.x;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void prod(Matrix m) {
		if (m==null) return;
		
		float xTemp = m.a1 * x + m.a2 * y + m.a3 * z;
		float yTemp = m.b1 * x + m.b2 * y + m.b3 * z;
		float zTemp = m.c1 * x + m.c2 * y + m.c3 * z;

		x = xTemp;
		y = yTemp;
		z = zTemp;
	}
}
