package ar.droid.admin.reader

import ar.droid.admin.Entity
import groovy.lang.GroovyClassLoader

class ReaderNews {
	def static lsReaderNews = [new ReaderNewsNone(), new ReaderNewsRSS(), new ReaderNewsWeb(), new ReaderNewsFacebook()]
	
	String parameter
	//Entity entity
	
	static belongsTo = [entity: Entity]
	
	static constraints = {
		parameter(nullable: true)
	}
	
	public static ReaderNews fromString(String keyValue) {
		def domainClass = keyValue.substring(6)
		return Class.forName(domainClass, true, new GroovyClassLoader()).newInstance()
	}
	
	public static String toString(ReaderNews objectValue) {
		return objectValue.getClass().toString()
	}
}
