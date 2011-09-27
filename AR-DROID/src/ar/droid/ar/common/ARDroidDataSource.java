package ar.droid.ar.common;

import java.util.List;

import ar.droid.model.Entity;
import ar.droid.model.Resource;

public class ARDroidDataSource extends DataSource {

	@Override
	public List<Entity> getEntities(double lat, double lon, double alt) {
		// retorno las entidades cacheadas
		return Resource.getInstance().getEntities();
	}
}
