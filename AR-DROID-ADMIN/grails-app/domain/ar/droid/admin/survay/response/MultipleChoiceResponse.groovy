package ar.droid.admin

class MultipleChoiceResponse extends Response{

	static hasMany =[options:Choice]
	
	MultipleChoiceQuestion multipleChoiceQuestion
	
    static constraints = {
    }
	
	
}
