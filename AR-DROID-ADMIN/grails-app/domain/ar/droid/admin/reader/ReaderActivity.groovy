package ar.droid.admin.reader

import ar.droid.admin.Entity
import groovy.lang.GroovyClassLoader

class ReaderActivity {
	
	String parameter
	Entity entity
	
	static belongsTo = [entity: Entity]
	
	static constraints = {
		parameter(nullable: true)
	}
	
	public static ReaderActivity fromString(String keyValue) {
		def domainClass = keyValue.substring(6)
		return Class.forName(domainClass, true, new GroovyClassLoader()).newInstance()
	}
	
	def synchronizeEvents(entityInstance, params){
	}
}