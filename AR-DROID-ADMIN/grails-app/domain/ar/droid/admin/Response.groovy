package ar.droid.admin

class Response {
	String comment
   
	static constraints = {
		comment(blank:false)
    }
}
