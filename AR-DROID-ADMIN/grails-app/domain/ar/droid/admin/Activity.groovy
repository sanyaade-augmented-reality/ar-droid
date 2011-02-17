package ar.droid.admin

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;

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
	static mapping = {
		description type: 'text'
	}
	static constraints = {
		url(url:true)
    }
}
