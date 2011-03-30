package ar.droid.admin

class TypeEvent {
	String name
	 
    static constraints = {
		name(blank:false)
    }
	
	@Override
	public String toString() {
		return name
	}
}
