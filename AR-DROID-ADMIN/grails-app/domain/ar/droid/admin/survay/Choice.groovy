package ar.droid.admin.survay

class Choice {
	String description
			
    static constraints = {
		description(blank: false)
    }
}
