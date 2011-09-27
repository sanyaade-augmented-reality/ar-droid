package ar.droid.ar.common;

import java.util.List;

import ar.droid.model.Entity;

public abstract class DataSource {
	protected static final int MAX = 10;

	public abstract List<Entity> getEntities(double lat, double lon, double alt);

}
