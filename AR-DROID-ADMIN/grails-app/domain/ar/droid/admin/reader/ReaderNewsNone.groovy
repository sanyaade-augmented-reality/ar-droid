package ar.droid.admin.reader

class ReaderNewsNone extends ReaderNews {
	
	static mapping = {
		tablePerHierarchy false
	}
	
	static constraints = {
		parameter(nullable: true)
	}
	
	@Override
	public String toString(){
		return "Ninguno";
	}
}
