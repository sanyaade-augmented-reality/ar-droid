package ar.droid.admin

class TypeEvent {
	String description
	String color
	 
    static constraints = {
		description(blank:false)
		color(blank: false)
    }
	
	@Override
	public String toString() {
		return description
	}
}
