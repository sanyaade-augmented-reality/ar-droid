package ar.droid.admin

import java.math.BigDecimal;



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
		name(blank: false)
		readerActivity(nullable:true)
		readerNews(nullable:true)
	}
	
}


/**De esta forma se mapea una composicion, GeoPoint NO se transforma en una tabla*/
class GeoPoint {
	BigDecimal latitude;
	BigDecimal longitude;
}