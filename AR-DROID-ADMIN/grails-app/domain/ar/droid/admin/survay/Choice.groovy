package ar.droid.admin

class Choice {
	String description
			
    static constraints = {
		description(blank: false)
    }
}
