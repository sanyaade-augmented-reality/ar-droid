package ar.droid.admin.reader

class ReaderActivityFacebook extends ReaderActivity {
	
	def readerActivityService
	static mapping = {
		tablePerHierarchy false
	}
	
	static constraints = {
		parameter(blank:false)
	}
	
	@Override
	public String toString(){
		return "Facebook"
	}
	
	def synchronizeEvents(entityInstance,params){
		readerActivityService.synchronizeEventsFacebook(entityInstance,params);
	}
}