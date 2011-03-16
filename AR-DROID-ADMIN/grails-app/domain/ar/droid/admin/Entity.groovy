package ar.droid.admin

import javax.swing.SpringLayout.Constraints;

class Entity {
	
	/**Mapeo uno a muchos, para que se actulize en casada
	 * es decir si borro una entidad se borre una actividad
	 * en la entidad Activity hay que definir la propiedad belongTo*/
	static hasMany = [activities: Activity]
	
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
	static mapping = {
		description type: 'text'
		photo sqlType: 'blob' // para archivos grandes
	}
}

/**De esta forma se mapea una composicion, GeoPoint NO se transforma en una tabla*/
class GeoPoint {
	BigDecimal latitude;
	BigDecimal longitude;
	
	@Override
	public String toString() {
		return this.latitude + '@' + this.longitude;
	}
}