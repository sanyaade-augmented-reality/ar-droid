package ar.droid.admin

import ar.droid.admin.reader.*

class Entity {

	/**Mapeo uno a muchos, para que se actulize en casada
	 * es decir si borro una entidad se borre una actividad
	 * en la entidad Activity hay que definir la propiedad belongTo*/
	static hasMany = [events: Event]

	String name
	String description
	String url
	byte[] photo
	TypeEntity typeEntity
	GeoPoint geoPoint
	static embedded = ['geoPoint']

	ReaderNews readerNews
	ReaderActivity readerActivity

	Date ultimaActualizacion
	long ultimoId

	/**con la siguiente sentencia se declaran validaciones, existen muchas predefinidas como la de url, etc*/
	static constraints = {
		url(url:true)
		name(blank: false)
		readerActivity(nullable: false)
		readerNews(nullable: false)
		photo(size:0..5000000)
	}
	static mapping = {
		description type: 'text'
		photo sqlType: 'blob' // para archivos grandes
	}

	@Override
	public String toString() {
		return name
	}

	public List activeEvents() {
		def act_events = []
		events.each {
			if(it.active())
				act_events.add(it)
		}
		return act_events
	}
}

/**De esta forma se mapea una composicion, GeoPoint NO se transforma en una tabla*/
class GeoPoint {
	Double latitude
	Double longitude
	Double altitude

	@Override
	public String toString() {
		return this.latitude + '@' + this.longitude
	}

	public boolean isAttached(){
		return false
	}
}