package ar.droid.admin

class Activity {
	
	static hasMany = [events:Event]
	
	String name
	String description
	String url
	byte[] photo
	
	TypeActivity typeActivity
	
	/*Conocimiento bidireccional con Entidad y con la propiedad belongTo cada vez que se
	 * elimine una Entidad se eliminan sus actividades*/
	static belongsTo = [entity:Entity]
	
	static constraints = {
		url(url:true)
    }
}
