package ar.droid.admin.reader

class ReaderNewsTwitter extends ReaderNews {
	
	static mapping = {
		tablePerHierarchy false
	}
	
    static constraints = {
		parameter(blank:false)
    }
	
	@Override
	public String toString(){
		return "Twitter";
	}
}
