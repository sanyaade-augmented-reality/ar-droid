package ar.droid.admin.reader

class ReaderActivityFacebook extends ReaderActivity {
	
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
}