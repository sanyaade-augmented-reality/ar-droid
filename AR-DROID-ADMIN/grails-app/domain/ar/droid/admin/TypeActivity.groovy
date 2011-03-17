package ar.droid.admin

class TypeActivity {
	
	String description
	String color

	static constraints = {
		description(blank: false)
		color(blank: false)
    }
	
	@Override
	public String toString() {
		return description
	}
}


