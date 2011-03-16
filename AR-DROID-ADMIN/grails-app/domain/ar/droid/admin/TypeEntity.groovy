package ar.droid.admin

class TypeEntity {

	String description
	byte[] icon
	
    static constraints = {
		description(blank: false)
		icon(nullable:true)
    }
	
	@Override
	public String toString() {
		return description
	}
	
	static mapping = {
		icon sqlType: 'blob'
	}
}
