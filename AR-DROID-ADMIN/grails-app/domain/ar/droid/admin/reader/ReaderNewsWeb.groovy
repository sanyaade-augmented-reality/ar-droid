package ar.droid.admin.reader

class ReaderNewsWeb extends ReaderNews {
	
	static mapping = {
		tablePerHierarchy false
	}
	
    static constraints = {
		parameter(url:true)
    }
	
	@Override
	public String toString(){
		return "Web";
	}
}
