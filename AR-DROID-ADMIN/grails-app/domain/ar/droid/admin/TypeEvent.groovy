package ar.droid.admin

class TypeEvent {
	String description
	 
    static constraints = {
		description(blank:false)
    }
	
	@Override
	public String toString() {
		return description
	}
}
