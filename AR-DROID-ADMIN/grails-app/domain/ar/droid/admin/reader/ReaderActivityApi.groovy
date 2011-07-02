package ar.droid.admin.reader

class ReaderActivityApi extends ReaderActivity {
	def readerActivityService
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static constraints = {
		parameter(url:true, blank:false)
	}
	
	@Override
	public String toString(){
		return "API AR-Droid"
	}
	
	def synchronizeEvents(entityInstance,params){
		readerActivityService.synchronizeEventsDroid(entityInstance,params);
	}
}