package ar.droid.admin.reader

class ReaderNewsFacebook extends ReaderNews {
		
	static mapping = {
		tablePerHierarchy false
	}
	
    static constraints = {
		parameter(blank:false)
    }
	
	@Override
	public String toString(){
		return "Facebook";
	}
}
