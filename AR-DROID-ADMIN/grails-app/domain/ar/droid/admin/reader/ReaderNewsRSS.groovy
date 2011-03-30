package ar.droid.admin.reader

class ReaderNewsRSS extends ReaderNews {
	
	static mapping = {
		tablePerHierarchy false
	}
	
    static constraints = {
		parameter(url:true, blank:false)
    }
	
	@Override
	public String toString(){
		return "RSS";
	}
}