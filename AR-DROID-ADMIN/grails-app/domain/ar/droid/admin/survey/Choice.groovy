package ar.droid.admin.survey

class Choice {
	String description
			
    static constraints = {
		description(blank: false)
    }
}
