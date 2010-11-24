package ar.droid.admin



class Entity {
	
	/**Mapeo uno a muchos, para que se actulize en casada
	 * es decir si borro una entidad se borre una actividad
	 * en la entidad Activity hay que definir la propiedad belongTo*/
	static hasMany = [activities:Activity]
	
	String name
	String description
	String url
	byte[] photo
	TypeEntity typeEntity
	GeoPoint geoPoint;
	static embedded = ['geoPoint']
	
	
	ReaderNews readerNews
	ReaderActivity readerActivity
	
	/**con la siguiente sentencia se declaran validaciones, existen muchas predefinidas
	 * como la de url, etc*/
	static constraints = {
		url(url:true)		
	}
}


/**De esta forma se mapea una composicion, GeoPoint NO se transforma en una tabla*/
class GeoPoint {
	Double latitude;
	Double longitude;
}