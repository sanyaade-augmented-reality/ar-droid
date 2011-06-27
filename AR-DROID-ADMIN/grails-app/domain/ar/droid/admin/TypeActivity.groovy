package ar.droid.admin

class TypeActivity {
	
	String description

	static constraints = {
		description(blank: false)
    }
	
	@Override
	public String toString() {
		return description
	}
}


