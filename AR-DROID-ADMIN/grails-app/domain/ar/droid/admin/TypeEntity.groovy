package ar.droid.admin

class TypeEntity {
	
	String description
	byte[] icon
	
	static constraints = {
		description(blank: false)
		icon(nullable: false)
	}
	
	static mapping = { icon sqlType: 'blob' }
	
	@Override
	public String toString() {
		return description
	}
}