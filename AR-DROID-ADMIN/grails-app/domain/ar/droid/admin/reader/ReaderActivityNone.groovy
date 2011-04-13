package ar.droid.admin.reader

class ReaderActivityNone extends ReaderActivity {
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static constraints = {
		parameter(nullable: true)
	}
	
	@Override
	public String toString(){
		return "Ninguno"
	}
	
	def synchronizeEvents(entityInstance,params){
		//No hace nada
	}
}